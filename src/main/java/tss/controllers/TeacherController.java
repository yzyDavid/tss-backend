package tss.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.configs.Config;
import tss.entities.ClassEntity;
import tss.entities.TimeSlotEntity;
import tss.entities.UserEntity;
import tss.exceptions.ClazzNotFoundException;
import tss.exceptions.TeacherNotFoundException;
import tss.models.Clazz;
import tss.models.TimeSlot;
import tss.models.TimeSlotTypeEnum;
import tss.repositories.ClassRepository;
import tss.repositories.UserRepository;

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

    @GetMapping("/{userId}/schedule")
    @ResponseStatus(HttpStatus.OK)
    public List<TimeSlot> getSchedule(@PathVariable String userId) {
        UserEntity teacherEntity = userRepository.findById(userId).orElseThrow(TeacherNotFoundException::new);
//        if (!Config.TYPES[2].equals(teacherEntity.readTypeName())) {
//            throw new TeacherNotFoundException();
//        }

        List<TimeSlot> schedule = new ArrayList<>(TimeSlotTypeEnum.values().length);
        for (ClassEntity classEntity : teacherEntity.getClassesTeaching()) {
            Clazz clazz = new Clazz(classEntity);
            for (TimeSlotEntity timeSlotEntity : classEntity.getTimeSlots()) {
                schedule.add(new TimeSlot(timeSlotEntity));
            }
        }
        return schedule;
    }
}
