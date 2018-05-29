package tss.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.*;
import tss.exceptions.*;
import tss.models.Clazz;
import tss.models.ClazzList;
import tss.repositories.*;
import tss.responses.information.GetClassesResponse;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author reeve
 */
@RestController
@RequestMapping()
public class ClassController {
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ClassRegistrationRepository classRegistrationRepository;

    @Autowired
    public ClassController(CourseRepository courseRepository, ClassRepository classRepository,
                           UserRepository userRepository, ClassroomRepository classroomRepository,
                           TimeSlotRepository timeSlotRepository, ClassRegistrationRepository classRegistrationRepository
                           ) {
        this.courseRepository = courseRepository;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.classRegistrationRepository = classRegistrationRepository;
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
        classEntity.setNumStudent(clazz.getNumStudent());
        classEntity.setTeacher(userEntity);
        classEntity.setCourse(courseEntity);

        classEntity = classRepository.save(classEntity);
        System.out.println(classEntity.getId());
        return new Clazz(classEntity);
    }

    @GetMapping(path = "/classes/{classId}")
    @ResponseStatus(HttpStatus.OK)
    public Clazz getClass(@PathVariable long classId) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);
        return new Clazz(classEntity);
    }

    @DeleteMapping(path = "/classes/{classId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeClass(@PathVariable long classId) {

        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);
        classRepository.delete(classEntity);
    }

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
                final int timeSlotSize = timeSlotEntity.getType().getSize();
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

    @GetMapping("/classes/search/findByCourse_NameAndYearAndSemester")
    @ResponseStatus(HttpStatus.OK)
    public GetClassesResponse searchClassByName(@RequestParam String courseName,
                                                @RequestParam(required = false) Integer year,
                                                @RequestParam(required = false) SemesterEnum semester) {
        List<ClassEntity> classes;
        if (year == null || semester == null) {
            //classes = classRepository.findByCourse_NameLike("%"+courseName+"%");
            classes = classRepository.findByCourse_Name("%"+courseName+"%");
        }
        else classes = classRepository.findByCourse_NameLikeAndYearAndSemester("%"+courseName+"%", year, semester);

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
        }
        else classes = classRepository.findByCourse_IdAndYearAndSemester(courseId, year, semester);
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

        List<UserEntity> teachers = userRepository.findByNameLikeAndType_Name("%"+teacherName+"%", "System Administrator");
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

    @PutMapping(path = "/classes/register")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public void registerClass(@RequestParam long classId, @RequestParam String userId) {
        ClassEntity clazz = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);
        UserEntity user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        /**
         * TODO: find the program of this user and confirm that the course is in it
         * CourseEntity course = clazz.getCourse();
        */

        if (!user.readTypeName().equals("Student")) {
            throw new UserNotStudentException();
        }
        ClassStatusEnum classStatusEnum = ClassStatusEnum.SELECTED;
        ClassRegistrationEntity classRegistrationEntity =
                new ClassRegistrationEntity(0, user, clazz, classStatusEnum, new Timestamp(System.currentTimeMillis()), null);
        classRegistrationRepository.save(classRegistrationEntity);
        /**
         *
         * TODO: the joint between tables
         */
    }

    @PutMapping(path = "/classes/confirm")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public void confirmClass(@RequestParam long classId, @RequestParam String userId) {
        ClassRegistrationId id = new ClassRegistrationId(userId, classId);
        Optional<ClassRegistrationEntity> cr = classRegistrationRepository.findById(id);
        if (!cr.isPresent()) {
            throw new ClassNotRegisteredException();
        }
        ClassRegistrationEntity classRegistration = cr.get();
        ClassStatusEnum status = classRegistration.getStatus();
        if (!status.equals(ClassStatusEnum.SELECTED)) {
            throw new ClassNotRegisteredException();
        }
        classRegistration.setStatus(ClassStatusEnum.CONFIRMED);
        classRegistration.setConfirmTime(new Timestamp(System.currentTimeMillis()));
        classRegistrationRepository.save(classRegistration);
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