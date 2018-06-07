package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.*;
import tss.exceptions.ClazzNotFoundException;
import tss.exceptions.CourseNotFoundException;
import tss.exceptions.TeacherNotFoundException;
import tss.models.ClassArrangementResult;
import tss.models.Clazz;
import tss.repositories.*;
import tss.responses.information.GetClassesResponse;

import java.util.*;

/**
 * @author reeve
 * @author NeverMore2744
 */
@RestController
@RequestMapping()
public class ClassController {
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassController(CourseRepository courseRepository, ClassRepository classRepository, UserRepository
            userRepository, ClassroomRepository classroomRepository) {
        this.courseRepository = courseRepository;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
    }

    @PostMapping("/courses/{courseId}/classes")
    @ResponseStatus(HttpStatus.CREATED)
    public Clazz insertClass(@PathVariable String courseId, @RequestBody Clazz clazz) {

        CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        UserEntity userEntity = userRepository.findById(clazz.getTeacherId()).orElseThrow
                (TeacherNotFoundException::new);

        ClassEntity classEntity = new ClassEntity();
        classEntity.setYear(clazz.getYear());
        classEntity.setSemester(clazz.getSemester());
        classEntity.setCapacity(clazz.getCapacity());
        classEntity.setTeacher(userEntity);
        classEntity.setCourse(courseEntity);

        classEntity = classRepository.save(classEntity);
        System.out.println(classEntity.getId());
        return new Clazz(classEntity);
    }

    @GetMapping("/courses/{courseId}/classes")
    public List<Clazz> listClasses(@PathVariable String courseId) {

        CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        List<Clazz> classes = new ArrayList<>();
        for (ClassEntity classEntity : courseEntity.getClasses()) {
            classes.add(new Clazz(classEntity));
        }
        return classes;
    }

    @GetMapping("/classes/{classId}")
    @ResponseStatus(HttpStatus.OK)
    public Clazz getClass(@PathVariable long classId) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);
        return new Clazz(classEntity);
    }

    @DeleteMapping("/classes/{classId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeClass(@PathVariable long classId) {

        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);
        classRepository.delete(classEntity);
    }

    @GetMapping("/classes/search/find-by-course-name-containing-and-year-and-semester")
    @ResponseStatus(HttpStatus.OK)
    public List<Clazz> findClassesByCourseNameContainingAndYearAndSemester(@RequestParam String courseName,
                                                                           @RequestParam int year,
                                                                           @RequestParam SemesterEnum semester) {

        List<ClassEntity> classEntities = classRepository.findByCourseNameContainingAndYearAndSemester(courseName,
                year, semester);
        List<Clazz> classes = new ArrayList<>();
        for (ClassEntity classEntity : classEntities) {
            classes.add(new Clazz(classEntity));
        }
        return classes;
    }

    @PutMapping("/auto-arrangement")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public ClassArrangementResult autoArrangement(@RequestParam int year, @RequestParam SemesterEnum semester) {
        // Clear old arrangement records.
        for (ClassroomEntity classroomEntity : classroomRepository.findAll()) {
            for (TimeSlotEntity timeSlotEntity : classroomEntity.getTimeSlots()) {
                timeSlotEntity.setClazz(null);
            }
        }

        // Wrap ClassEntities with ClassItems.
        List<ClassItem> classItems = new ArrayList<>();
        for (ClassEntity classEntity : classRepository.findByYearAndSemester(year, semester)) {
            ClassItem classItem = new ClassItem(classEntity);
            if (!classItem.hasNoLeftSection()) {
                classItems.add(classItem);
            }
        }

        // Auto-arrange.
        int count = 0;
        for (ClassroomEntity classroomEntity : classroomRepository.findAll()) {
            if (classItems.size() == 0) {
                break;
            }

            for (TimeSlotEntity timeSlotEntity : classroomEntity.getTimeSlots()) {
                if (classItems.size() == 0) {
                    break;
                }

                final int timeSlotSize = timeSlotEntity.getType().getSize();
                for (int i = 0; i < classItems.size(); i++) {
                    ClassItem classItem = classItems.get(i);

                    if (classItem.getNumLeftSectionsOfSize(timeSlotSize) > 0) {
                        classItem.getClassEntity().addTimeSlot(timeSlotEntity);
                        classItem.setNumLeftSectionsOfSize(timeSlotSize, classItem.getNumLeftSectionsOfSize(timeSlotSize) - 1);
                        if (classItem.hasNoLeftSection()) {
                            Collections.swap(classItems, i, classItems.size() - 1);
                            classItems.remove(classItems.size() - 1);
                            count++;
                        }
                        break;
                    }
                }
            }
        }

        // Statics.
        List<Long> pendingClassIds = new ArrayList<>();
        for (ClassItem classItem : classItems) {
            pendingClassIds.add(classItem.getClassId());
        }
        return new ClassArrangementResult(count, pendingClassIds);
    }

    @GetMapping("/classes/search/findByCourse_NameAndYearAndSemester")
    @ResponseStatus(HttpStatus.OK)
    public GetClassesResponse searchClassByName(@RequestParam String courseName,
                                                @RequestParam(required = false) Integer year,
                                                @RequestParam(required = false) SemesterEnum semester) {
        List<ClassEntity> classes;
        if (year == null || semester == null) {
            //classes = classRepository.findByCourse_NameLike("%"+courseName+"%");
            classes = classRepository.findByCourse_Name("%" + courseName + "%");
        } else {
            classes = classRepository.findByCourse_NameLikeAndYearAndSemester("%" + courseName + "%", year, semester);
        }

        if (classes.isEmpty()) {
            System.out.println("Empty");
        }

        return new GetClassesResponse(classes);
    }

    @GetMapping("/classes/search/findByCourse_IdAndYearAndSemester")
    @ResponseStatus(HttpStatus.OK)
    public GetClassesResponse searchClassById(@RequestParam String courseId,
                                              @RequestParam(required = false) Integer year,
                                              @RequestParam(required = false) SemesterEnum semester) {
        List<ClassEntity> classes;

        if (year == null || semester == null) {
            classes = classRepository.findByCourse_Id(courseId);
        } else {
            classes = classRepository.findByCourse_IdAndYearAndSemester(courseId, year, semester);
        }
        /*
        if (classes.isEmpty()) {
            ...
        }*/

        return new GetClassesResponse(classes);
    }

    @GetMapping("/classes/search/findByTeacher_NameAndYearAndSemester")
    @ResponseStatus(HttpStatus.OK)
    public GetClassesResponse searchClassByTeacher(@RequestParam String teacherName,
                                                   @RequestParam(required = false) Integer year,
                                                   @RequestParam(required = false) SemesterEnum semester) {

        List<UserEntity> teachers = userRepository.findByNameLikeAndType_Name("%" + teacherName + "%", "System Administrator");
        /*
        if (teachers.isEmpty()) {
            ...
        }*/

        List<ClassEntity> classesAll = new ArrayList<>();
        for (UserEntity teacher : teachers) {
            List<ClassEntity> classes = teacher.getClassesTeaching();
            if (year == null || semester == null) {
                classesAll.addAll(classes);
                continue;
            }
            for (ClassEntity clazz : classes) {
                if (clazz.getYear().equals(year) && clazz.getSemester().equals(semester)) {
                    classesAll.add(clazz);
                }
            }
        }

        return new GetClassesResponse(classesAll);
    }

    @GetMapping("/classes/search/findByBoth")
    @ResponseStatus(HttpStatus.OK)
    public GetClassesResponse searchClassByBoth(@RequestParam String courseName,
                                                @RequestParam String teacherName,
                                                @RequestParam(required = false) Integer year,
                                                @RequestParam(required = false) SemesterEnum semester) {

        if (year == null || semester == null) {
            Set<ClassEntity> classesByCourseName = new HashSet<>(classRepository.findByCourse_NameLike(courseName));
            List<UserEntity> teachers = userRepository.findByName(teacherName);
            /* if (classes.isEmpty() || teachers.isEmpty()) { ... } */
            Set<ClassEntity> classesByTeacherName = new HashSet<>();
            for (UserEntity teacher : teachers) {
                if (!teacher.readTypeName().equals("Teacher")) {
                    continue;
                }
                classesByTeacherName.addAll(teacher.getClassesTeaching());
            }
            Set<ClassEntity> res = new HashSet<>();
            res.clear();
            res.addAll(classesByCourseName);
            res.retainAll(classesByTeacherName);
            return new GetClassesResponse(new ArrayList<>(res));
        }

        Set<ClassEntity> classesByCourseName = new HashSet<>(classRepository.findByCourse_NameLikeAndYearAndSemester(courseName, year, semester));
        List<UserEntity> teachers = userRepository.findByName(teacherName);
        Set<ClassEntity> classesByTeacherName = new HashSet<>();
        for (UserEntity teacher : teachers) {
            if (!teacher.readTypeName().equals("Teacher")) {
                continue;
            }
            List<ClassEntity> classes = teacher.getClassesTeaching();
            for (ClassEntity clazz : classes) {
                if (clazz.getYear().equals(year) && clazz.getSemester().equals(semester)) {
                    classesByTeacherName.add(clazz);
                }
            }
        }
        Set<ClassEntity> res = new HashSet<>();
        res.clear();
        res.addAll(classesByCourseName);
        res.retainAll(classesByTeacherName);

        return new GetClassesResponse(new ArrayList<>(res));
    }

    @PutMapping(path = "/classes/select")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public void registerClass(@CurrentUser UserEntity user,
                              @PathVariable long classId) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);


    }


}

/**
 * Auxiliary class for auto-arrangement of classes.
 */
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
                break;
            case 7:
                numLeftSectionsOfSizeTwo = 2;
                numLeftSectionsOfSizeThree = 1;
                break;
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

    @Override
    public String toString() {
        return "ClassItem{" +
                "classId=" + classId +
                ", numLeftSectionsOfSizeTwo=" + numLeftSectionsOfSizeTwo +
                ", numLeftSectionsOfSizeThree=" + numLeftSectionsOfSizeThree +
                ", classEntity=" + classEntity +
                '}';
    }
}