package tss.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.ClassEntity;
import tss.entities.TimeSlotEntity;
import tss.entities.UserEntity;
import tss.exceptions.ClazzNotFoundException;
import tss.exceptions.TeacherNotFoundException;
import tss.models.Clazz;
import tss.models.TimeSlotTypeEnum;
import tss.repositories.ClassRepository;
import tss.repositories.UserRepository;

import java.util.HashMap;
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
    @Authorization
    public void addClassTeaching(@CurrentUser UserEntity user,
                                 @PathVariable String userId, @PathVariable long classId) {
        UserEntity teacherEntity = userRepository.findById(userId).orElseThrow(TeacherNotFoundException::new);
        if (!"Teacher".equals(teacherEntity.readTypeName())) {
            throw new TeacherNotFoundException();
        }
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);

        teacherEntity.addClassTeaching(classEntity);
        userRepository.save(teacherEntity);
    }

    @GetMapping("/{userId}/schedule")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Clazz> getSchedule(@PathVariable String userId) {
        UserEntity teacherEntity = userRepository.findById(userId).orElseThrow(TeacherNotFoundException::new);
        if (!"Teacher".equals(teacherEntity.readTypeName())) {
            throw new TeacherNotFoundException();
        }

        Map<String, Clazz> schedule = new HashMap<>(TimeSlotTypeEnum.values().length);
        for (ClassEntity classEntity : teacherEntity.getClassesTeaching()) {
            Clazz clazz = new Clazz(classEntity);
            for (TimeSlotEntity timeSlotEntity : classEntity.getTimeSlots()) {
                schedule.put(timeSlotEntity.getTypeName(), clazz);
            }
        }
        return schedule;
    }
}
