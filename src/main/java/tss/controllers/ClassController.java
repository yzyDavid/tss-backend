package tss.controllers;

import org.apache.catalina.User;
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
import tss.models.ClazzList;
import tss.repositories.*;
import tss.requests.information.AddClassRegistrationRequest;
import tss.requests.information.ConfirmClassRequest;
import tss.requests.information.GetClassesForSelectionRequest;
import tss.requests.information.ModifyClassRegistrationRequest;
import tss.responses.information.BasicResponse;
import tss.responses.information.GetClassesResponse;
import tss.responses.information.GetSelectedClassesResponse;

import javax.persistence.Basic;
import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author reeve
 * @author NeverMore2744 - ljh
 */
@RestController
@RequestMapping()
public class ClassController {
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final ProgramRepository programRepository;
    private final ProgramCourseRepository programCourseRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final SelectionTimeRepository selectionTimeRepository;
    private final ClassRegistrationRepository classRegistrationRepository;


    @Autowired
    public ClassController(CourseRepository courseRepository, ClassRepository classRepository,
                           ProgramRepository programRepository, ProgramCourseRepository programCourseRepository,
                           UserRepository userRepository, ClassroomRepository classroomRepository,
                           TimeSlotRepository timeSlotRepository, SelectionTimeRepository selectionTimeRepository,
                           ClassRegistrationRepository classRegistrationRepository) {
        this.courseRepository = courseRepository;
        this.classRepository = classRepository;
        this.programRepository = programRepository;
        this.programCourseRepository = programCourseRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.selectionTimeRepository = selectionTimeRepository;
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

    @PostMapping("/classes/search")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public GetClassesResponse searchClasses(@CurrentUser UserEntity user,
                                            @RequestBody GetClassesForSelectionRequest request) {
        List<ClassEntity> classes;
        List<Boolean> selected = new ArrayList<>();

        // Error: Program not found
        ProgramEntity programEntity = programRepository.findByPid(user.getUid()).orElseThrow(ProgramNotFoundException::new);

        // 1. Use ID to search
        if (request.getCourseId() != null) {
            // Error 1: Course not found
            CourseEntity courseEntity;
            courseEntity = courseRepository.findById(request.getCourseId()).orElseThrow(CourseNotFoundException::new);
            // Error 2: Courses not found in program
            if (!programCourseRepository.existsByCourseAndProgram(courseEntity, programEntity)) {
                throw new CourseNotFoundInProgramException();
            }
            // Error 3: Classes not found
            classes = classRepository.findByCourse_Id(request.getCourseId());
            if (classes.isEmpty()) {
                throw new ClazzNotFoundException();
            }
            for (ClassEntity classEntity : classes) {
                if (classRegistrationRepository.existsByStudentAndClazz(user, classEntity)) {
                    selected.add(Boolean.TRUE);
                }
                else
                    selected.add(Boolean.FALSE);
            }
            return new GetClassesResponse(classes, selected);
        }

        if (request.getCourseName() != null) {
            if (request.getTeacherName() != null) {
                // 2. Use courseName and teacherName
                classes = new ArrayList<>();
                List<CourseEntity> courseEntityList = courseRepository.findByNameLike(request.getCourseName());

                for (CourseEntity courseEntity : courseEntityList) {
                    if (!programCourseRepository.existsByCourseAndProgram(courseEntity, programEntity))
                        continue;

                    List<ClassEntity> classEntityList = classRepository.findByCourse_IdAndTeacher_Name(courseEntity.getId(), request.getTeacherName());
                    classes.addAll(classEntityList);
                }

                if (classes.isEmpty()) {
                    throw new ClazzNotFoundException();
                }
            }
            else {
                // 3. Use only courseName to search
                classes = new ArrayList<>();
                List<CourseEntity> courseEntityList = courseRepository.findByNameLike(request.getCourseName());

                for (CourseEntity courseEntity : courseEntityList) {
                    if (!programCourseRepository.existsByCourseAndProgram(courseEntity, programEntity))
                        continue;

                    List<ClassEntity> classEntityList = classRepository.findByCourse_Id(courseEntity.getId());
                    classes.addAll(classEntityList);
                }

                if (classes.isEmpty()) {
                    throw new ClazzNotFoundException();
                }
            }
        }
        else if (request.getTeacherName() != null) {
            // 4. Use only teacher name to search
            classes = new ArrayList<>();

            List<ClassEntity> classesList = classRepository.findByTeacher_NameLike(request.getTeacherName());

            Set<CourseEntity> courseEntities = new HashSet<>(); // Temporary
            Set<CourseEntity> courseEntities1 = new HashSet<>();  // courses in program
            for (ClassEntity classEntity : classesList) {
                CourseEntity courseEntity = classEntity.getCourse();
                if (courseEntities.contains(courseEntity))
                    continue;
                courseEntities.add(courseEntity);
                if (!programCourseRepository.existsByCourseAndProgram(courseEntity, programEntity))
                    continue;
                courseEntities1.add(courseEntity);
            }

            for (CourseEntity courseEntity : courseEntities1) {
                List<ClassEntity> classEntityList = classRepository.findByCourse_IdAndTeacher_Name(courseEntity.getId(), request.getTeacherName());
                classes.addAll(classEntityList);
            }

            if (classes.isEmpty()) {
                throw new ClazzNotFoundException();
            }
        }
        else {
            throw new ClassSearchInvalidException();
        }

        for (ClassEntity classEntity : classes) {
            if (classRegistrationRepository.existsByStudentAndClazz(user, classEntity)) {
                selected.add(Boolean.TRUE);
            }
            else
                selected.add(Boolean.FALSE);
        }
        return new GetClassesResponse(classes, selected);
    }

    @PostMapping(path = "/classes/register")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public BasicResponse addClassRegistration(@CurrentUser UserEntity user, @RequestBody AddClassRegistrationRequest request) {
        long classId = request.getClassId();
        // Error 1: The class doesn't exist
        ClassEntity clazz = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);

        // Error 2: The user is not a student
        if (!user.readTypeName().equals("Student")) {  // The operator is not a student
            throw new UserNotStudentException();
        }
        ClassStatusEnum classStatusEnum = ClassStatusEnum.SELECTED;
        String crid = user.getUid()+"CR"+classId;

        // Error 3: The class has been registered
        if (classRegistrationRepository.existsByStudentAndClazz_Course(user, clazz.getCourse())) {
            throw new ClassRegisteredException();
        }

        // Error 4: The program doesn't exist
        ProgramEntity programEntity = programRepository.findByPid(user.getUid()).orElseThrow(ProgramNotFoundException::new);

        CourseEntity courseEntity = clazz.getCourse();

        // Error 5: The program_course doesn't exist
        if (!programCourseRepository.existsByCourseAndProgram(courseEntity, programEntity)) {
            throw new CourseNotFoundInProgramException();
        }

        // Error 6: Not in the selection time
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (!selectionTimeRepository.existsByStartLessThanEqualAndEndGreaterThanEqualAndRegisterTrue(currentTime, currentTime)) {
            throw new SelectionTimeInvalidRegisterException();
        }

        ClassRegistrationEntity classRegistrationEntity =
                new ClassRegistrationEntity(0, user, clazz, crid,
                        classStatusEnum, new Timestamp(System.currentTimeMillis()), null);
                //new ClassRegistrationEntity(0, user, clazz, classStatusEnum, new Timestamp(System.currentTimeMillis()), null);

        // Error 6: Classroom is full of students
        if (clazz.getNumStudent() >= clazz.getCapacity())
            throw new ClassFullException();
        clazz.setNumStudent(clazz.getNumStudent() + 1);
        classRepository.save(clazz);

        classRegistrationRepository.save(classRegistrationEntity);

        return new BasicResponse("Class registered successfully!");
    }
/*
    @PutMapping(path = "/classes/confirm")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public BasicResponse confirmClass(@RequestBody ConfirmClassRequest request) {
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

        return new BasicResponse("Class confirmed");
    }*/

    @PutMapping(path = "/classes/finish")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public BasicResponse finishClass(@RequestBody ModifyClassRegistrationRequest request) {
        String studentId = request.getUid();
        Long classId = request.getClassId();

        // Error 1: Class not found
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);

        // Error 2: Student not found
        UserEntity student = userRepository.findById(studentId).orElseThrow(UserNotFoundException::new);

        // Error 3: User not a student
        if (!student.readTypeName().equals("Student")) {
            throw new UserNotStudentException();
        }

        // Error 4: Class not registered
        String id = studentId + "CR" + classId.toString();
        ClassRegistrationEntity classRegistration = classRegistrationRepository.findByCrid(id).orElseThrow(ClassNotRegisteredException::new);

        // Error 5: Status errors
        ClassStatusEnum status = classRegistration.getStatus();
        if (!status.equals(ClassStatusEnum.SELECTED)) {
            if (status.equals(ClassStatusEnum.FINISHED))
                throw new ClassFinishedException();
            else if (status.equals(ClassStatusEnum.FAILED))
                throw new ClassFailedException();
        }

        classRegistration.setStatus(ClassStatusEnum.FINISHED);
        classRegistration.setScore(request.getScore());
        classRegistration.setConfirmTime(new Timestamp(System.currentTimeMillis()));
        classRegistrationRepository.save(classRegistration);

        return new BasicResponse("Class finished.");
    }

    @PutMapping(path = "/classes/fail")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public BasicResponse failClass(@RequestBody ConfirmClassRequest request) {
        String studentId = request.getUid();
        Long classId = request.getClassId();

        // Error 1: Class not found
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);

        // Error 2: Student not found
        UserEntity student = userRepository.findById(studentId).orElseThrow(UserNotFoundException::new);

        // Error 3: User not a student
        if (!student.readTypeName().equals("Student")) {
            throw new UserNotStudentException();
        }

        // Error 4: Class not registered
        String id = studentId + "CR" + classId.toString();
        ClassRegistrationEntity classRegistration = classRegistrationRepository.findByCrid(id).orElseThrow(ClassNotRegisteredException::new);

        // Error 5: Status errors
        ClassStatusEnum status = classRegistration.getStatus();
        if (!status.equals(ClassStatusEnum.SELECTED)) {
            if (status.equals(ClassStatusEnum.FINISHED))
                throw new ClassFinishedException();
            else if (status.equals(ClassStatusEnum.FAILED))
                return new BasicResponse("Class failed.");
        }

        classRegistration.setStatus(ClassStatusEnum.FAILED);
        classRegistration.setConfirmTime(new Timestamp(System.currentTimeMillis()));
        classRegistrationRepository.save(classRegistration);

        return new BasicResponse("Class failed.");
    }

    @PostMapping(path = "/classes/complement")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public BasicResponse complementClass(@CurrentUser UserEntity user, @RequestBody AddClassRegistrationRequest request) {

        long classId = request.getClassId();
        // Error 1: The class doesn't exist
        ClassEntity clazz = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);

        // Error 2: The user is not a student
        if (!user.readTypeName().equals("Student")) {  // The operator is not a student
            throw new UserNotStudentException();
        }
        ClassStatusEnum classStatusEnum = ClassStatusEnum.SELECTED;
        String crid = user.getUid()+"CR"+classId;

        // Error 3: The class has been registered
        if (classRegistrationRepository.existsByStudentAndClazz_Course(user, clazz.getCourse())) {
            throw new ClassRegisteredException();
        }

        // Error 4: The program doesn't exist
        ProgramEntity programEntity = programRepository.findByPid(user.getUid()).orElseThrow(ProgramNotFoundException::new);

        CourseEntity courseEntity = clazz.getCourse();

        // Error 5: The program_course doesn't exist
        if (!programCourseRepository.existsByCourseAndProgram(courseEntity, programEntity)) {
            throw new CourseNotFoundInProgramException();
        }

        // Error 6: Not in the selection time
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (!selectionTimeRepository.existsByStartLessThanEqualAndEndGreaterThanEqualAndRegisterTrue(currentTime, currentTime)) {
            throw new SelectionTimeInvalidRegisterException();
        }

        ClassRegistrationEntity classRegistrationEntity =
                new ClassRegistrationEntity(0, user, clazz, crid,
                        classStatusEnum, new Timestamp(System.currentTimeMillis()), null);
        //new ClassRegistrationEntity(0, user, clazz, classStatusEnum, new Timestamp(System.currentTimeMillis()), null);

        // Error 7: Classroom is full of students
        if (clazz.getNumStudent() >= clazz.getCapacity())
            throw new ClassFullException();
        clazz.setNumStudent(clazz.getNumStudent() + 1);
        classRepository.save(clazz);

        classRegistrationRepository.save(classRegistrationEntity);

        return new BasicResponse("Class filled successfully!");
    }

    @DeleteMapping(path = "/classes/drop")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public BasicResponse dropClass(@CurrentUser UserEntity user, @RequestBody AddClassRegistrationRequest request) {
        String userId = user.getUid();
        Long classId = request.getClassId();

        // Error 1: Class not found
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);

        // Error 2: User not a student
        if (!user.readTypeName().equals("Student")) {
            throw new UserNotStudentException();
        }

        // Error 3: Class not registered
        String Id = userId + "CR" + classId.toString();
        ClassRegistrationEntity cr = classRegistrationRepository.findByCrid(Id).orElseThrow(ClassNotRegisteredException::new);

        // Error 4: Status errors
        ClassStatusEnum status = cr.getStatus();
        if (status.equals(ClassStatusEnum.FINISHED)) {
            throw new ClassFinishedNotForDroppingException();
        }

        // Error 5: Not in the drop time
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (!selectionTimeRepository.existsByStartLessThanEqualAndEndGreaterThanEqualAndDropTrue(currentTime, currentTime)) {
            throw new SelectionTimeInvalidRegisterException();
        }

        classRegistrationRepository.delete(cr);
        classEntity.setNumStudent(classEntity.getNumStudent()-1);
        classRepository.save(classEntity);

        return new BasicResponse("Class dropped.");
    }

    // semester = "FIRST" / "SECOND"
    @GetMapping(path = "/classes/get_selected/{year}/{semester}")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public GetSelectedClassesResponse getClassesTable(
            @CurrentUser UserEntity user,
            @PathVariable Integer year,
            @PathVariable SemesterEnum semester) {

        if (!user.readTypeName().equals("Student")) {
            throw new UserNotStudentException();
        }

        List<ClassEntity> classesSelected = new ArrayList<>();
        List<ClassRegistrationEntity> classesRegistered = classRegistrationRepository.findByStudent(user);

        for (ClassRegistrationEntity cr : classesRegistered) {
            ClassEntity clazz = cr.getClazz();
            if (!clazz.getYear().equals(year) || !clazz.getSemester().equals(semester))
                continue;
            classesSelected.add(clazz);
        }
        return new GetSelectedClassesResponse(classesSelected);
    }

    @PostMapping(path = "/classes/admin_register")
    @Authorization
    @ResponseStatus(HttpStatus.OK)
    public BasicResponse adminRegisterClass(@CurrentUser UserEntity user, @RequestBody ConfirmClassRequest request) {
        String studentId = request.getUid();
        long classId = request.getClassId();
        // Error 1: The class doesn't exist
        ClassEntity clazz = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);

        // Error 2: No admin or no student
        if (!user.readTypeName().equals("Teaching Administrator") && !user.readTypeName().equals("System Administrator")) {
            throw new UserNotAdminException();
        }
        UserEntity student = userRepository.findByUid(studentId).orElseThrow(UserNotFoundException::new);
        if (!student.readTypeName().equals("Student")) {
            throw new UserNotStudentException();
        }

        // Error 3: The class has been registered
        String crid = user.getUid()+"CR"+classId;
        if (classRegistrationRepository.existsByStudentAndClazz_Course(student, clazz.getCourse())) {
            throw new ClassRegisteredException();
        }

        // Error 4: The program doesn't exist
        ProgramEntity programEntity = programRepository.findByPid(user.getUid()).orElseThrow(ProgramNotFoundException::new);

        CourseEntity courseEntity = clazz.getCourse();

        // Error 5: The program_course doesn't exist
        if (!programCourseRepository.existsByCourseAndProgram(courseEntity, programEntity)) {
            throw new CourseNotFoundInProgramException();
        }

        ClassStatusEnum classStatusEnum = ClassStatusEnum.SELECTED;
        ClassRegistrationEntity classRegistrationEntity =
                new ClassRegistrationEntity(0, user, clazz, crid,
                        classStatusEnum, new Timestamp(System.currentTimeMillis()), null);
        //new ClassRegistrationEntity(0, user, clazz, classStatusEnum, new Timestamp(System.currentTimeMillis()), null);

        // Error 6: Classroom is full of students
        if (clazz.getNumStudent() >= clazz.getCapacity())
            throw new ClassFullException();
        clazz.setNumStudent(clazz.getNumStudent() + 1);
        classRepository.save(clazz);

        classRegistrationRepository.save(classRegistrationEntity);

        return new BasicResponse("Class registered by admin successfully!");
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