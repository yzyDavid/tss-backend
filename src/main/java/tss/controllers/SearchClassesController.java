package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.ClassEntity;
import tss.requests.information.GetClassesBySearchingNameRequest;
import tss.requests.information.GetClassesBySearchingTeacherRequest;
import tss.requests.information.GetClassesBySearchingBothRequest;
import tss.responses.information.GetClassesBySearchingNameResponse;
import tss.responses.information.GetClassesBySearchingTeacherResponse;
import tss.responses.information.GetClassesBySearchingBothResponse;


import java.util.*;

import tss.entities.*;
import tss.repositories.*;

@Controller
@RequestMapping(path = "/search_classes")
public class SearchClassesController {
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final TeachesRepository teachesRepository;
    private final UserRepository userRepository;

    @Autowired
    public SearchClassesController(CourseRepository courseRepository, ClassRepository classRepository,
                           TeachesRepository teachesRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.teachesRepository = teachesRepository;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
    }

    @PutMapping(path = "/search")
    @Authorization
    public ResponseEntity<GetClassesBySearchingNameResponse> searchClassByName(@CurrentUser UserEntity user,
                                                     @RequestBody GetClassesBySearchingNameRequest request) {
        String name = request.getName();

        List<CourseEntity> ret = courseRepository.findByName(name);
        if (ret.isEmpty()) {
            return new ResponseEntity<>(new GetClassesBySearchingNameResponse("course doesn't exist",null),
                    HttpStatus.BAD_REQUEST);
        }

        Set<ClassEntity> classesAll = new HashSet<>();

        for (CourseEntity course : ret) {
            classesAll.addAll(course.getClasses());
        }

        return new ResponseEntity<>(new GetClassesBySearchingNameResponse("OK", classesAll), HttpStatus.OK);
    }

    @PostMapping(path = "/by_teacher")
    @Authorization
    public ResponseEntity<GetClassesBySearchingTeacherResponse> modifyClass(@CurrentUser UserEntity user,
                                                           @RequestBody GetClassesBySearchingTeacherRequest request) {
        String name = request.getName();

        List<UserEntity> ret = userRepository.findByName(name);

        if (ret.isEmpty()) {
            return new ResponseEntity<>(new GetClassesBySearchingTeacherResponse("course doesn't exist", null),
                    HttpStatus.BAD_REQUEST);
        }

        Set<ClassEntity> classesAll = new HashSet<>();
        for (UserEntity teacher : ret) {
            if (!teacher.readTypeName().equals("Teacher")) {
                continue;
            }
            Set<TeachesEntity> teachesAll = teacher.getTeaches();
            for (TeachesEntity teaches : teachesAll) {
                Set<ClassEntity> classes = teaches.getClasses();
                classesAll.addAll(classes);
            }
        }

        return new ResponseEntity<>(new GetClassesBySearchingTeacherResponse("OK", classesAll), HttpStatus.OK);
    }

    @DeleteMapping(path = "/by_course_and_teacher")
    @Authorization
    public ResponseEntity<GetClassesBySearchingBothResponse> deleteClasses(@CurrentUser UserEntity user,
                                                               @RequestBody GetClassesBySearchingBothRequest request) {
        String courseName = request.getCourseName();
        String teacherName = request.getTeacherName();

        List<CourseEntity> ret = courseRepository.findByName(courseName);
        if (ret.isEmpty()) {
            return new ResponseEntity<>(new GetClassesBySearchingBothResponse("course doesn't exist", null),
                    HttpStatus.BAD_REQUEST);
        }

        List<UserEntity> retu = userRepository.findByName(teacherName);

        if (retu.isEmpty()) {
            return new ResponseEntity<>(new GetClassesBySearchingBothResponse("course doesn't exist", null),
                    HttpStatus.BAD_REQUEST);
        }

        Set<ClassEntity> classesByCourseName = new HashSet<>();
        Set<ClassEntity> classesByTeacherName = new HashSet<>();

        for (CourseEntity course : ret) {
            classesByCourseName.addAll(course.getClasses());
        }

        for (UserEntity teacher : retu) {
            if (!teacher.readTypeName().equals("Teacher")) {
                continue;
            }
            Set<TeachesEntity> teachesAll = teacher.getTeaches();
            for (TeachesEntity teaches : teachesAll) {
                Set<ClassEntity> classes = teaches.getClasses();
                classesByTeacherName.addAll(classes);
            }
        }

        Set<ClassEntity> res = new HashSet<>();
        res.clear();
        res.addAll(classesByCourseName);
        res.retainAll(classesByTeacherName);

        return new ResponseEntity<>(new GetClassesBySearchingBothResponse("OK", res), HttpStatus.OK);

    }

}