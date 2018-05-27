package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.entities.ClassEntity;
import tss.entities.ClassroomEntity;
import tss.entities.TimeSlotEntity;
import tss.exceptions.ClassroomNotFoundException;
import tss.exceptions.ClazzNotFoundException;
import tss.exceptions.TimeSlotTypeNotFoundException;
import tss.models.Classroom;
import tss.models.Clazz;
import tss.models.TimeSlotTypeEnum;
import tss.repositories.ClassRepository;
import tss.repositories.ClassroomRepository;
import tss.repositories.TimeSlotRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cbq
 * @author reeve
 */
@RestController
@RequestMapping("/classrooms")
public class ClassroomController {
    private final ClassroomRepository classroomRepository;
    private final ClassRepository classRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public ClassroomController(ClassroomRepository classroomRepository, ClassRepository classRepository,
                               TimeSlotRepository timeSlotRepository) {
        this.classroomRepository = classroomRepository;
        this.classRepository = classRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    @GetMapping("/{classroomId}")
    @ResponseStatus(HttpStatus.OK)
    public Classroom getClassroom(@PathVariable int classroomId) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId).orElseThrow
                (ClassroomNotFoundException::new);
        return new Classroom(classroomEntity);
    }

    @DeleteMapping("/{classroomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeClassroom(@PathVariable int classroomId) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId).orElseThrow
                (ClassroomNotFoundException::new);
        classroomRepository.delete(classroomEntity);
    }

    @PatchMapping("/{classroomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClassroom(@PathVariable int classroomId, @RequestBody Classroom classroom) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId).orElseThrow
                (ClassroomNotFoundException::new);
        if (classroom.getName() != null) {
            classroomEntity.setName(classroom.getName());
        }
        if (classroom.getCapacity() != null) {
            classroomEntity.setCapacity(classroom.getCapacity());
        }
        classroomRepository.save(classroomEntity);
    }

    @PutMapping("/{classroomId}/time-slots/{timeSlotType}/clazz")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void insertArrangement(@PathVariable int classroomId, @PathVariable TimeSlotTypeEnum timeSlotType,
                                  @RequestParam long classId) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId).orElseThrow
                (ClassroomNotFoundException::new);
        TimeSlotEntity timeSlotEntity = classroomEntity.getTimeSlotDirectory().get(timeSlotType);
        if (timeSlotEntity == null) {
            throw new TimeSlotTypeNotFoundException();
        }
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);

        classEntity.addTimeSlot(timeSlotEntity);
        timeSlotRepository.save(timeSlotEntity);
    }

    @DeleteMapping("/{classroomId}/time-slots/{timeSlotType}/clazz")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeArrangement(@PathVariable int classroomId, @PathVariable TimeSlotTypeEnum timeSlotType) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId).orElseThrow
                (ClassroomNotFoundException::new);
        TimeSlotEntity timeSlotEntity = classroomEntity.getTimeSlotDirectory().get(timeSlotType);
        if (timeSlotEntity == null) {
            throw new TimeSlotTypeNotFoundException();
        }

        timeSlotEntity.setClazz(null);
        timeSlotRepository.save(timeSlotEntity);
    }

    @GetMapping("/{classroomId}/schedule")
    @ResponseStatus(HttpStatus.OK)
    public Map<TimeSlotTypeEnum, Clazz> getSchedule(@PathVariable int classroomId) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId).orElseThrow
                (ClassroomNotFoundException::new);

        Map<TimeSlotTypeEnum, Clazz> schedule = new HashMap<>(TimeSlotTypeEnum.values().length);
        for (TimeSlotEntity timeSlotEntity : classroomEntity.getTimeSlots()) {
            Clazz clazz;
            if (timeSlotEntity.getClazz() == null) {
                clazz = null;
            } else {
                clazz = new Clazz(timeSlotEntity.getClazz());
            }
            schedule.put(timeSlotEntity.getType(), clazz);
        }
        return schedule;
    }
}
