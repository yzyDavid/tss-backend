package tss.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.configs.Config;
import tss.entities.ClassEntity;
import tss.entities.SemesterEnum;
import tss.entities.TimeSlotEntity;
import tss.entities.UserEntity;
import tss.exceptions.ClazzNotFoundException;
import tss.exceptions.TeacherNotFoundException;
import tss.models.Clazz;
import tss.models.TimeSlot;
import tss.models.TimeSlotTypeEnum;
import tss.repositories.ClassRepository;
import tss.repositories.UserRepository;


import java.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author reeve
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    public TeacherController(UserRepository userRepository, ClassRepository classRepository) {
        this.userRepository = userRepository;
        this.classRepository = classRepository;
    }

    @PutMapping("/{userId}/classes-teaching/{classId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addClassTeaching(@CurrentUser UserEntity user,
                                 @PathVariable String userId, @PathVariable long classId) {
        UserEntity teacherEntity = userRepository.findById(userId).orElseThrow(TeacherNotFoundException::new);
        if (!Config.TYPES[2].equals(teacherEntity.readTypeName())) {
            throw new TeacherNotFoundException();
        }
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);

        teacherEntity.addClassTeaching(classEntity);
        userRepository.save(teacherEntity);

    }

    @GetMapping("/{userId}/classes")
    @ResponseStatus(HttpStatus.OK)
    public List<Clazz> listClassesTeaching(@PathVariable String userId, @RequestParam int year,
                                           @RequestParam SemesterEnum semester) {

        UserEntity teacherEntity = userRepository.findById(userId).orElseThrow(TeacherNotFoundException::new);
        // TODO: commented for testing.
//        if (!Config.TYPES[2].equals(teacherEntity.readTypeName())) {
//            throw new TeacherNotFoundException();
//        }

        List<Clazz> classes = new ArrayList<>();
        for (ClassEntity classEntity : teacherEntity.getClassesTeaching()) {
            if (classEntity.getYear() == year && semester.equals(classEntity.getSemester())) {
                classes.add(new Clazz(classEntity));
            }
        }
        return classes;
    }
}
