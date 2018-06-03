package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.*;
import tss.exceptions.*;
import tss.models.ClassArrangementResult;
import tss.models.Clazz;
import tss.repositories.*;
import tss.requests.information.AddClassRegistrationRequest;
import tss.requests.information.ConfirmClassRequest;
import tss.requests.information.ModifyClassRegistrationRequest;
import tss.responses.information.GetClassesResponse;
import tss.responses.information.GetSelectedClassesResponse;

import java.sql.Timestamp;
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

    @GetMapping("/classes/search/findByCourseName")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public GetClassesResponse searchClassByName(@RequestParam String name,
                                                @RequestParam(required = false) Integer year,
                                                @RequestParam(required = false) SemesterEnum semester) {
        List<ClassEntity> classes;
        if (year == null || semester == null) {
            //classes = classRepository.findByCourse_NameLike("%"+courseName+"%");
            classes = classRepository.findByCourse_Name("%"+name+"%");
        }
        else classes = classRepository.findByCourse_NameLikeAndYearAndSemester("%"+name+"%", year, semester);

        if (classes.isEmpty()) {
            System.out.println("Empty");
        }

        return new GetClassesResponse(classes);
    }

    @GetMapping("/classes/search/findByCourseId")
    @Authorization
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

    @GetMapping("/classes/search/findByTeacher")
    @Authorization
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

    @PostMapping(path = "/classes/register")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public void addClassRegistration(@CurrentUser UserEntity user, @RequestBody AddClassRegistrationRequest request) {
        long classId = request.getClassId();
        System.out.print(classId);
        ClassEntity clazz = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);
        /*
         * TODO: find the program of this user and confirm that the course is in it
         * CourseEntity course = clazz.getCourse();
        */

        if (!user.readTypeName().equals("Student")) {
            throw new UserNotStudentException();
        }
        ClassStatusEnum classStatusEnum = ClassStatusEnum.SELECTED;
        String crid = user.getUid()+"CR"+classId;
        ClassRegistrationEntity classRegistrationEntity =
                new ClassRegistrationEntity(0, user, clazz, crid,
                        classStatusEnum, new Timestamp(System.currentTimeMillis()), null);
                //new ClassRegistrationEntity(0, user, clazz, classStatusEnum, new Timestamp(System.currentTimeMillis()), null);
        classRegistrationRepository.save(classRegistrationEntity);
        /*
         *
         * TODO: the joint between tables
         */
    }

    @PutMapping(path = "/classes/confirm")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public void confirmClass(@RequestBody ConfirmClassRequest request) {
        String userId = request.getUid();
        Long classId = request.getClassId();
        Optional<ClassEntity> clazz = classRepository.findById(classId);
        if (!clazz.isPresent()) {
            throw new ClazzNotFoundException();
        }
        Optional<UserEntity> student = userRepository.findById(userId);
        if (!student.isPresent() || !student.get().readTypeName().equals("Student")) {
            throw new UserNotStudentException();
        }

        //ClassRegistrationId id = new ClassRegistrationId(student.get(), clazz.get());
        String id = userId + "CR" + classId.toString();
        Optional<ClassRegistrationEntity> cr = classRegistrationRepository.findByCrid(id);
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

    @PutMapping(path = "/classes/finish")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public void finishClass(@RequestBody ModifyClassRegistrationRequest request) {
        String userId = request.getUid();
        Long classId = request.getClassId();
        Optional<ClassEntity> clazz = classRepository.findById(classId);
        if (!clazz.isPresent()) {
            throw new ClazzNotFoundException();
        }
        Optional<UserEntity> student = userRepository.findById(userId);
        if (!student.isPresent() || !student.get().readTypeName().equals("Student")) {
            throw new UserNotStudentException();
        }

        //ClassRegistrationId id = new ClassRegistrationId(student.get(), clazz.get());
        String id = userId + "CR" + classId.toString();
        Optional<ClassRegistrationEntity> cr = classRegistrationRepository.findByCrid(id);
        if (!cr.isPresent()) {
            throw new ClassNotRegisteredException();
        }
        ClassRegistrationEntity classRegistration = cr.get();
        ClassStatusEnum status = classRegistration.getStatus();
        if (!status.equals(ClassStatusEnum.SELECTED)) {
            throw new ClassNotRegisteredException();
        }
        classRegistration.setStatus(ClassStatusEnum.FINISHED);
        classRegistration.setScore(request.getScore());
        classRegistration.setConfirmTime(new Timestamp(System.currentTimeMillis()));
        classRegistrationRepository.save(classRegistration);
    }

    @PutMapping(path = "/classes/fail")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public void failClass(@RequestBody ConfirmClassRequest request) {
        String userId = request.getUid();
        Long classId = request.getClassId();
        Optional<ClassEntity> clazz = classRepository.findById(classId);
        if (!clazz.isPresent()) {
            throw new ClazzNotFoundException();
        }
        Optional<UserEntity> student = userRepository.findById(userId);
        if (!student.isPresent() || !student.get().readTypeName().equals("Student")) {
            throw new UserNotStudentException();
        }

        //ClassRegistrationId id = new ClassRegistrationId(student.get(), clazz.get());
        String id = userId + "CR" + classId.toString();
        Optional<ClassRegistrationEntity> cr = classRegistrationRepository.findByCrid(id);
        if (!cr.isPresent()) {
            throw new ClassNotRegisteredException();
        }
        ClassRegistrationEntity classRegistration = cr.get();
        ClassStatusEnum status = classRegistration.getStatus();
        if (!status.equals(ClassStatusEnum.SELECTED)) {
            throw new ClassNotRegisteredException();
        }
        classRegistration.setStatus(ClassStatusEnum.FAILED);
        classRegistration.setConfirmTime(new Timestamp(System.currentTimeMillis()));
        classRegistrationRepository.save(classRegistration);
    }

    // semester = "FIRST" / "SECOND"
    @GetMapping(path = "/get_selected/{year}/{semester}")
    @Authorization
    public GetSelectedClassesResponse getClassesTable(
            @CurrentUser UserEntity user,
            @RequestParam Integer year,
            @RequestParam SemesterEnum semester) {

        List<ClassEntity> classesSelected = new ArrayList<>();
        List<ClassRegistrationEntity> classesRegistered = classRegistrationRepository.findByStudent(user);

        for (ClassRegistrationEntity cr : classesRegistered) {
            classesSelected.add(cr.getClazz());
        }

        return new GetSelectedClassesResponse(classesSelected);
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