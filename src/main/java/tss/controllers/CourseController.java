package tss.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.entities.*;
import tss.repositories.*;
import tss.requests.information.*;
import tss.responses.information.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mingqi Yi
 */
@Controller
@RequestMapping(path = "/course")
public class CourseController {
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;


    @Autowired
    CourseController(CourseRepository courseRepository, DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
    }

    @PutMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddCourseResponse> addCourse(@CurrentUser UserEntity user,
                                                       @RequestBody AddCourseRequest request) {
        String cid = request.getCid();
        if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new AddCourseResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
        } else if (courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new AddCourseResponse("failed with duplicated cid", null, null), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity dept = null;
        if(request.getDept() != null) {
            Optional<DepartmentEntity> ret = departmentRepository.findByName(request.getDept());
            if(!ret.isPresent()) {
                return new ResponseEntity<>(new AddCourseResponse("Department doesn't exist", null, null), HttpStatus.BAD_REQUEST);
            }
            dept = ret.get();
        }
        CourseEntity course = new CourseEntity();
        course.setId(request.getCid());
        course.setName(request.getName());
        course.setCredit(request.getCredit());
        course.setNumLessonsEachWeek(request.getNumLessonsEachWeek());
        course.setDepartment(dept);
        courseRepository.save(course);

        return new ResponseEntity<>(new AddCourseResponse("ok", course.getId(), course.getName()), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteCourseResponse> deleteCourse(@CurrentUser UserEntity user,
                                                             @RequestBody DeleteCourseRequest request) {
        String cid = request.getCid();
        if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new DeleteCourseResponse("course non-exist"), HttpStatus.BAD_REQUEST);
        } else if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new DeleteCourseResponse("permission denied"), HttpStatus.FORBIDDEN);
        }

        courseRepository.deleteById(cid);

        return new ResponseEntity<>(new DeleteCourseResponse("ok"), HttpStatus.OK);

    }


    @PostMapping(path = "/modify")
    @Authorization
    public ResponseEntity<ModifyCourseResponse> modifyInfo(@CurrentUser UserEntity user,
                                                           @RequestBody ModifyCourseRequest request) {
        String cid = request.getCid();
        if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new ModifyCourseResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
        }
        Optional<CourseEntity> ret = courseRepository.findById(cid);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyCourseResponse("course doesn't exist", null, null), HttpStatus.BAD_REQUEST);
        }
        CourseEntity course = ret.get();
        DepartmentEntity dept = null;
        if(request.getDept() != null) {
            Optional<DepartmentEntity> retd = departmentRepository.findByName(request.getDept());
            if(!retd.isPresent()) {
                return new ResponseEntity<>(new ModifyCourseResponse("department doesn't exist", null, null), HttpStatus.BAD_REQUEST);
            }
            course.setDepartment(retd.get());
        }

        if(request.getName() != null) {
            course.setName(request.getName());
        }
        if(request.getCredit() != null) {
            course.setCredit(request.getCredit());
        }
        if(request.getNumLessonsEachWeek() != null) {
            course.setNumLessonsEachWeek(request.getNumLessonsEachWeek());
        }
        if(request.getIntro() != null) {
            course.setIntro(request.getIntro());
        }
        courseRepository.save(course);

        return new ResponseEntity<>(new ModifyCourseResponse("ok", "", ""), HttpStatus.OK);
    }

    @PostMapping(path = "/get")
    @Authorization
    public ResponseEntity<GetCourseResponse> getInfo(@RequestBody GetCourseRequest request) {
        Optional<CourseEntity> ret = courseRepository.findById(request.getCid());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetCourseResponse("course non-exist", "", "", 0.0f,
                    null, ""), HttpStatus.BAD_REQUEST);
        }
        CourseEntity course = ret.get();
        return new ResponseEntity<>(new GetCourseResponse("ok", course.getId(), course.getName(),
                course.getCredit(), course.getNumLessonsEachWeek(), course.getIntro()), HttpStatus.OK);
    }

    @PostMapping(path = "/name")
    @Authorization
    public ResponseEntity<GetCoursesByNameResponse> getCidsByName(@RequestBody GetCoursesByNameRequest request) {
        List<String> cids = new ArrayList<>();
        List<CourseEntity> ret = courseRepository.findByName(request.getName());
        for(CourseEntity user : ret) {
            cids.add(user.getId());
        }
        return new ResponseEntity<>(new GetCoursesByNameResponse("OK", cids), HttpStatus.OK);
    }
}
