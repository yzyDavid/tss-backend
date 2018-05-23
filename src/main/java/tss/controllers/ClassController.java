package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.*;
import tss.exceptions.ClazzNotFoundException;
import tss.models.TimeSlotTypeEnum;
import tss.repositories.ClassRepository;
import tss.repositories.ClassroomRepository;
import tss.repositories.CourseRepository;
import tss.repositories.TimeSlotRepository;
import tss.requests.information.AddClassRequest;
import tss.requests.information.DeleteClassesRequest;
import tss.requests.information.GetInstructorsRequest;
import tss.requests.information.ModifyClassRequest;
import tss.responses.information.AddClassResponse;
import tss.responses.information.DeleteClassesResponse;
import tss.responses.information.GetInstructorResponse;
import tss.responses.information.ModifyClassResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Mingqi Yi
 * @author reeve
 */
@Controller
@RequestMapping(path = "/class")
public class ClassController {
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final ClassroomRepository classroomRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public ClassController(CourseRepository courseRepository, ClassRepository classRepository, ClassroomRepository classroomRepository, TimeSlotRepository timeSlotRepository) {
        this.courseRepository = courseRepository;
        this.classRepository = classRepository;
        this.classroomRepository = classroomRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    @PutMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddClassResponse> addClass(@CurrentUser UserEntity user,
                                                     @RequestBody AddClassRequest request) {
        String cid = request.getCid();

        Optional<CourseEntity> ret = courseRepository.findById(cid);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new AddClassResponse("course doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        CourseEntity course = ret.get();
        ClassEntity newClass = new ClassEntity();
        newClass.setCapacity(request.getCapacity());
        newClass.setYear(request.getYear());
        newClass.setCourse(course);
        classRepository.save(newClass);
        return new ResponseEntity<>(new AddClassResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = "/info")
    @Authorization
    public ResponseEntity<ModifyClassResponse> modifyClass(@CurrentUser UserEntity user,
                                                           @RequestBody ModifyClassRequest request) {
        Long cid = request.getCid();

        Optional<ClassEntity> ret = classRepository.findById(cid);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyClassResponse("can't find the class"), HttpStatus.BAD_REQUEST);
        }
        ClassEntity c = ret.get();
        if (request.getCapacity() != null) {
            c.setCapacity(request.getCapacity());
        }
        if (request.getYear() != null) {
            c.setYear(request.getYear());
        }
        classRepository.save(c);
        return new ResponseEntity<>(new ModifyClassResponse("OK"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteClassesResponse> deleteClasses(@CurrentUser UserEntity user,
                                                               @RequestBody DeleteClassesRequest request) {
        List<Long> cids = request.getIds();

        List<Long> failIds = new ArrayList<>();
        for (Long cid : cids) {
            if (!classRepository.existsById(cid)) {
                failIds.add(cid);
            }
        }
        if (!failIds.isEmpty()) {
            return new ResponseEntity<>(new DeleteClassesResponse("Some Class don't exist", failIds), HttpStatus.BAD_REQUEST);
        }
        for (Long cid : cids) {
            classRepository.deleteById(cid);
        }
        return new ResponseEntity<>(new DeleteClassesResponse("OK", failIds), HttpStatus.OK);
    }

    @PostMapping(path = "/getInstructor")
    @Authorization
    public ResponseEntity<GetInstructorResponse> getInstructor(@RequestBody GetInstructorsRequest request) {
        ClassEntity classEntity = classRepository.findById(request.getCid()).orElseThrow(ClazzNotFoundException::new);
        UserEntity teacherEntity = classEntity.getTeacher();

        return new ResponseEntity<>(new GetInstructorResponse("ok", teacherEntity.getUid(), teacherEntity.getName()),
                HttpStatus.OK);
    }

    /**
     * @author reeve
     */
    @PutMapping(path = "/auto-arrangement")
    @ResponseStatus(HttpStatus.OK)
    public void autoArrangement() {
        List<ClassItem> classItems = new ArrayList<>();
        for (ClassEntity classEntity : classRepository.findAll()) {
            ClassItem classItem = new ClassItem(classEntity);
            if (!classItem.hasNoLeftSection()) {
                classItems.add(classItem);
            }
        }

        for (ClassroomEntity classroomEntity : classroomRepository.findAll()) {
            if (classItems.size() == 0) {
                break;
            }
            for (TimeSlotEntity timeSlotEntity : classroomEntity.getTimeSlots()) {
                if (classItems.size() == 0) {
                    break;
                }
                final int timeSlotSize = TimeSlotTypeEnum.valueOf(timeSlotEntity.getTypeName()).getSize();
                for (int i = 0; i < classItems.size(); i++) {
                    ClassItem classItem = classItems.get(i);
                    if (classItem.getNumLeftSectionsOfSize(timeSlotSize) > 0) {
                        classItem.getClassEntity().addTimeSlot(timeSlotEntity);
                        timeSlotRepository.save(timeSlotEntity);
                        classItem.setNumLeftSectionsOfSize(timeSlotSize, classItem.getNumLeftSectionsOfSize(timeSlotSize) - 1);
                        if (classItem.hasNoLeftSection()) {
                            Collections.swap(classItems, i, classItems.size());
                            classItems.remove(classItems.size());
                        }
                        break;
                    }
                }
            }
        }
    }

    class ClassItem {
        private long classId;
        private int numLeftSectionsOfSizeTwo = 0;
        private int numLeftSectionsOfSizeThree = 0;
        private ClassEntity classEntity;

        ClassItem(ClassEntity classEntity) {
            switch (classEntity.getCourse().getNumLessonsEachWeek()) {
                case 2:
                    numLeftSectionsOfSizeTwo = 1;
                    break;
                case 3:
                    numLeftSectionsOfSizeThree = 1;
                    break;
                case 4:
                    numLeftSectionsOfSizeTwo = 2;
                    break;
                case 5:
                    numLeftSectionsOfSizeTwo = 1;
                    numLeftSectionsOfSizeThree = 1;
                    break;
                case 6:
                    numLeftSectionsOfSizeThree = 2;
                default:
            }
            classId = classEntity.getId();
            this.classEntity = classEntity;
        }

        int getNumLeftSectionsOfSize(int size) {
            switch (size) {
                case 2:
                    return numLeftSectionsOfSizeTwo;
                case 3:
                    return numLeftSectionsOfSizeThree;
                default:
                    return 0;
            }
        }

        void setNumLeftSectionsOfSize(int size, int value) {
            switch (size) {
                case 2:
                    numLeftSectionsOfSizeTwo = value;
                    break;
                case 3:
                    numLeftSectionsOfSizeThree = value;
                    break;
                default:
            }
        }

        boolean hasNoLeftSection() {
            return numLeftSectionsOfSizeTwo + numLeftSectionsOfSizeThree == 0;
        }

        long getClassId() {
            return classId;
        }

        void setClassId(long classId) {
            this.classId = classId;
        }

        ClassEntity getClassEntity() {
            return classEntity;
        }

        void setClassEntity(ClassEntity classEntity) {
            this.classEntity = classEntity;
        }
    }
}
