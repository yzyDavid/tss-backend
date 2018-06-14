package tss.controllers;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.*;
import tss.exceptions.*;
import tss.repositories.*;
import tss.requests.information.AddClassRegistrationRequest;
import tss.requests.information.ConfirmClassRequest;
import tss.requests.information.GetClassesForSelectionRequest;
import tss.requests.information.ModifyClassRegistrationRequest;
import tss.responses.information.BasicResponse;
import tss.responses.information.GetClassesResponse;
import tss.responses.information.GetSelectedClassesResponse;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.util.*;


@Controller
@RequestMapping()
public class ClassSelectionController {
    private final ProgramCourseRepository programCourseRepository;
    private final ClassRegistrationRepository classRegistrationRepository;
    private final SelectionTimeRepository selectionTimeRepository;
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    public ClassSelectionController(ProgramCourseRepository programCourseRepository,
                                    ClassRegistrationRepository classRegistrationRepository,
                                    SelectionTimeRepository selectionTimeRepository, CourseRepository courseRepository,
                                    ClassRepository classRepository, UserRepository userRepository) {
        this.programCourseRepository = programCourseRepository;
        this.classRegistrationRepository = classRegistrationRepository;
        this.selectionTimeRepository = selectionTimeRepository;
        this.courseRepository = courseRepository;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/classes/search")
    @Authorization
    public ResponseEntity<GetClassesResponse> searchClasses(@CurrentUser UserEntity user,
                                        @RequestBody GetClassesForSelectionRequest request) {
        List<ClassEntity> classes;
        List<Boolean> selected = new ArrayList<>();
        List<Integer> numOfStudents = new ArrayList<>();

        // 1. Use ID to search
        if (request.getCourseId() != null) {
            // Error 1: Course not found
            CourseEntity courseEntity;
            Optional<CourseEntity> courseEntityOptional = courseRepository.findById(request.getCourseId());
            if (!courseEntityOptional.isPresent()) {
                return new ResponseEntity<>(new GetClassesResponse("课程不存在！",
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                        HttpStatus.BAD_REQUEST);
            }
            courseEntity = courseEntityOptional.get();

            // Error 2: Courses not found in program
            if (user != null && user.readTypeName().equals("Student") &&
                    !programCourseRepository.existsByCourseAndStudent(courseEntity, user)) {
                return new ResponseEntity<>(new GetClassesResponse("该课程在培养方案中不存在！",
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                        HttpStatus.BAD_REQUEST);
            }

            // Error 3: Classes not found
            classes = classRepository.findByCourse_Id(request.getCourseId());
            if (classes.isEmpty()) {
                return new ResponseEntity<>(new GetClassesResponse("没有该课程所对应的教学班！",
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                        HttpStatus.BAD_REQUEST);
            }
            for (ClassEntity classEntity : classes) {
                if (user != null && user.readTypeName().equals("Student") &&
                        classRegistrationRepository.existsByStudentAndClazz(user, classEntity)) {
                    selected.add(Boolean.TRUE);
                }
                else
                    selected.add(Boolean.FALSE);
                numOfStudents.add(classRegistrationRepository.countByClazz(classEntity));
            }
            return new ResponseEntity<>(new GetClassesResponse("搜索成功！", classes, selected, numOfStudents),
                    HttpStatus.OK);
        }

        if (request.getCourseName() != null) {
            if (request.getTeacherName() != null) {
                // 2. Use courseName and teacherName
                classes = new ArrayList<>();
                List<CourseEntity> courseEntityList = courseRepository.findByNameLike("%"+request.getCourseName()+"%");


                for (CourseEntity courseEntity : courseEntityList) {
                    if (user != null && user.readTypeName().equals("Student") &&
                            !programCourseRepository.existsByCourseAndStudent(courseEntity, user))
                        continue;

                    List<ClassEntity> classEntityList = classRepository.findByCourse_IdAndTeacher_NameLike(courseEntity.getId(),
                            request.getTeacherName());
                    classes.addAll(classEntityList);
                }

                if (classes.isEmpty()) {
                    return new ResponseEntity<>(new GetClassesResponse("找不到相应的教学班！",
                            new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                            HttpStatus.BAD_REQUEST);
                }
            }
            else {
                // 3. Use only courseName to search
                classes = new ArrayList<>();
                List<CourseEntity> courseEntityList = courseRepository.findByNameLike("%"+request.getCourseName()+"%");

                for (CourseEntity courseEntity : courseEntityList) {
                    if (user != null && user.readTypeName().equals("Student") &&
                            !programCourseRepository.existsByCourseAndStudent(courseEntity, user))
                        continue;

                    List<ClassEntity> classEntityList = classRepository.findByCourse_Id(courseEntity.getId());
                    classes.addAll(classEntityList);
                }

                if (classes.isEmpty()) {
                    return new ResponseEntity<>(new GetClassesResponse("找不到相应的教学班！",
                            new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                            HttpStatus.BAD_REQUEST);
                }
            }
        }
        else if (request.getTeacherName() != null) {
            // 4. Use only teacher name to search
            classes = new ArrayList<>();

            List<ClassEntity> classesList = classRepository.findByTeacher_NameLike("%"+request.getTeacherName()+"%");

            Set<CourseEntity> courseEntities = new HashSet<>(); // Temporary
            Set<CourseEntity> courseEntities1 = new HashSet<>();  // courses in program
            for (ClassEntity classEntity : classesList) {
                CourseEntity courseEntity = classEntity.getCourse();
                if (courseEntities.contains(courseEntity))
                    continue;
                courseEntities.add(courseEntity);
                if (user != null && user.readTypeName().equals("Student") &&
                        !programCourseRepository.existsByCourseAndStudent(courseEntity, user))
                    continue;
                courseEntities1.add(courseEntity);
            }

            for (CourseEntity courseEntity : courseEntities1) {
                List<ClassEntity> classEntityList = classRepository.findByCourse_IdAndTeacher_NameLike(
                        courseEntity.getId(),
                        "%"+request.getTeacherName()+"%");
                classes.addAll(classEntityList);
            }

            if (classes.isEmpty()) {
                return new ResponseEntity<>(new GetClassesResponse("找不到相应的教学班！",
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                        HttpStatus.BAD_REQUEST);
            }
        }
        else {
            throw new ClassSearchInvalidException();
        }

        for (ClassEntity classEntity : classes) {
            if (user != null && user.readTypeName().equals("Student") &&
                    classRegistrationRepository.existsByStudentAndClazz(user, classEntity)) {
                selected.add(Boolean.TRUE);
            }
            else
                selected.add(Boolean.FALSE);
            numOfStudents.add(classRegistrationRepository.countByClazz(classEntity));
        }
        return new ResponseEntity<>(new GetClassesResponse("搜索成功！", classes, selected, numOfStudents), HttpStatus.OK);
    }

    @PostMapping(path = "/classes/register")
    @Authorization
    public ResponseEntity<BasicResponse> addClassRegistration(@CurrentUser UserEntity user,
                                                              @RequestBody AddClassRegistrationRequest request) {
        long classId = request.getClassId();
        // Error 1: The class doesn't exist
        Optional<ClassEntity> classEntityOptional = classRepository.findById(classId);
        if (!classEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("找不到相应的教学班！"),
                    HttpStatus.BAD_REQUEST);
        }
        ClassEntity clazz = classEntityOptional.get();

        // Error 2: The user is not a student
        if (!user.readTypeName().equals("Student")) {  // The operator is not a student
            return new ResponseEntity<>(new BasicResponse("你不是学生，无法选课！"),
                    HttpStatus.FORBIDDEN);
        }
        ClassStatusEnum classStatusEnum = ClassStatusEnum.SELECTED;
        String crid = user.getUid()+"CR"+classId;

        // Error 3: The class has been registered
        if (classRegistrationRepository.existsByStudentAndClazz_Course(user, clazz.getCourse())) {
            return new ResponseEntity<>(new BasicResponse("该课程无法重复选择！"),
                    HttpStatus.BAD_REQUEST);
        }

        CourseEntity courseEntity = clazz.getCourse();

        // Error 4: The program_course doesn't exist
        if (!programCourseRepository.existsByCourseAndStudent(courseEntity, user)) {
            return new ResponseEntity<>(new BasicResponse("该课程在培养方案中不存在！"),
                    HttpStatus.BAD_REQUEST);
        }

        // Error 5: Not in the selection time
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (!selectionTimeRepository.existsByStartLessThanEqualAndEndGreaterThanEqualAndRegisterTrue(currentTime, currentTime)) {
            return new ResponseEntity<>(new BasicResponse("现在不是选课时间！"),
                    HttpStatus.BAD_REQUEST);
        }

        ClassRegistrationEntity classRegistrationEntity =
                new ClassRegistrationEntity(0, user, clazz, crid,
                        classStatusEnum, new Timestamp(System.currentTimeMillis()), null);

        // Error 6: Classroom is full of students
        if (classRegistrationRepository.countByClazz(clazz) >= clazz.getCapacity())
            return new ResponseEntity<>(new BasicResponse("该教学班人数已满！"),
                    HttpStatus.BAD_REQUEST);
        classRepository.save(clazz);

        classRegistrationRepository.save(classRegistrationEntity);

        return new ResponseEntity<>(new BasicResponse("选课成功！"+courseEntity.getName()), HttpStatus.OK);
    }

    @PutMapping(path = "/classes/finish")
    @Authorization
    public ResponseEntity<BasicResponse> finishClass(@RequestBody ModifyClassRegistrationRequest request) {
        String studentId = request.getUid();
        Long classId = request.getClassId();

        // Error 1: Class not found
        Optional<ClassEntity> classEntityOptional = classRepository.findById(classId);
        if (!classEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("找不到对应的教学班！"),
                    HttpStatus.BAD_REQUEST);
        }

        // Error 2: Student not found
        Optional<UserEntity> userEntityOptional = userRepository.findById(studentId);
        if (!userEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("找不到该学生！"),
                    HttpStatus.FORBIDDEN);
        }
        UserEntity student = userEntityOptional.get();

        // Error 3: User not a student
        if (student.readTypeName().equals("Student")) {
            return new ResponseEntity<>(new BasicResponse("找不到该学生！"),
                    HttpStatus.BAD_REQUEST);
        }

        // Error 4: Class not registered
        String id = studentId + "CR" + classId.toString();
        Optional<ClassRegistrationEntity> classRegistrationEntityOptional = classRegistrationRepository.findByCrid(id);
        if (!classRegistrationEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("该课程还未注册！"),
                    HttpStatus.BAD_REQUEST);
        }
        ClassRegistrationEntity classRegistration = classRegistrationEntityOptional.get();

        // Error 5: Status errors
        ClassStatusEnum status = classRegistration.getStatus();
        if (!status.equals(ClassStatusEnum.SELECTED)) {
            if (status.equals(ClassStatusEnum.FINISHED) || status.equals(ClassStatusEnum.FAILED))
                return new ResponseEntity<>(new BasicResponse("该课程已经结束！"),
                        HttpStatus.BAD_REQUEST);
        }

        classRegistration.setStatus(ClassStatusEnum.FINISHED);
        classRegistration.setScore(request.getScore());
        classRegistration.setConfirmTime(new Timestamp(System.currentTimeMillis()));
        classRegistrationRepository.save(classRegistration);

        return new ResponseEntity<>(new BasicResponse("课程结束。"), HttpStatus.OK);
    }

    @PutMapping(path = "/classes/fail")
    @Authorization
    public ResponseEntity<BasicResponse> failClass(@RequestBody ConfirmClassRequest request) {
        String studentId = request.getUid();
        Long classId = request.getClassId();

        // Error 1: Class not found
        Optional<ClassEntity> classEntityOptional = classRepository.findById(classId);
        if (!classEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("找不到对应的教学班！"),
                    HttpStatus.BAD_REQUEST);
        }

        // Error 2: Student not found
        Optional<UserEntity> userEntityOptional = userRepository.findById(studentId);
        if (!userEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("找不到该学生！"),
                    HttpStatus.FORBIDDEN);
        }
        UserEntity student = userEntityOptional.get();

        // Error 3: User not a student
        if (student.readTypeName().equals("Student")) {
            return new ResponseEntity<>(new BasicResponse("找不到该学生！"),
                    HttpStatus.BAD_REQUEST);
        }

        // Error 4: Class not registered
        String id = studentId + "CR" + classId.toString();
        Optional<ClassRegistrationEntity> classRegistrationEntityOptional = classRegistrationRepository.findByCrid(id);
        if (!classRegistrationEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("该课程还未注册！"),
                    HttpStatus.BAD_REQUEST);
        }
        ClassRegistrationEntity classRegistration = classRegistrationEntityOptional.get();

        // Error 5: Status errors
        ClassStatusEnum status = classRegistration.getStatus();
        if (!status.equals(ClassStatusEnum.SELECTED)) {
            if (status.equals(ClassStatusEnum.FINISHED) || status.equals(ClassStatusEnum.FAILED))
                return new ResponseEntity<>(new BasicResponse("该课程已经结束！"),
                        HttpStatus.BAD_REQUEST);
        }

        classRegistration.setStatus(ClassStatusEnum.FAILED);
        classRegistration.setConfirmTime(new Timestamp(System.currentTimeMillis()));
        classRegistrationRepository.save(classRegistration);

        return new ResponseEntity<>(new BasicResponse("成功登记该学生的不及格信息。"), HttpStatus.OK);
    }

    @PostMapping(path = "/classes/complement")
    @Authorization
    public ResponseEntity<BasicResponse> complementClass(@CurrentUser UserEntity user, @RequestBody AddClassRegistrationRequest request) {
        long classId = request.getClassId();
        // Error 1: The class doesn't exist
        Optional<ClassEntity> classEntityOptional = classRepository.findById(classId);
        if (!classEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("找不到相应的教学班！"),
                    HttpStatus.BAD_REQUEST);
        }
        ClassEntity clazz = classEntityOptional.get();

        // Error 2: The user is not a student
        if (!user.readTypeName().equals("Student")) {  // The operator is not a student
            return new ResponseEntity<>(new BasicResponse("你不是学生，无法选课！"),
                    HttpStatus.FORBIDDEN);
        }
        ClassStatusEnum classStatusEnum = ClassStatusEnum.SELECTED;
        String crid = user.getUid()+"CR"+classId;

        // Error 3: The class has been registered
        if (classRegistrationRepository.existsByStudentAndClazz_Course(user, clazz.getCourse())) {
            return new ResponseEntity<>(new BasicResponse("该课程无法重复选择！"),
                    HttpStatus.BAD_REQUEST);
        }

        CourseEntity courseEntity = clazz.getCourse();

        // Error 4: The program_course doesn't exist
        if (!programCourseRepository.existsByCourseAndStudent(courseEntity, user)) {
            return new ResponseEntity<>(new BasicResponse("该课程在培养方案中不存在！"),
                    HttpStatus.BAD_REQUEST);
        }

        // Error 5: Not in the selection time
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (!selectionTimeRepository.existsByStartLessThanEqualAndEndGreaterThanEqualAndComplementTrue(currentTime, currentTime)) {
            return new ResponseEntity<>(new BasicResponse("现在不是补选时间！"),
                    HttpStatus.BAD_REQUEST);
        }

        ClassRegistrationEntity classRegistrationEntity =
                new ClassRegistrationEntity(0, user, clazz, crid,
                        classStatusEnum, new Timestamp(System.currentTimeMillis()), null);

        // Error 6: Classroom is full of students
        if (classRegistrationRepository.countByClazz(clazz) >= clazz.getCapacity())
            return new ResponseEntity<>(new BasicResponse("该教学班人数已满！"),
                    HttpStatus.BAD_REQUEST);
        classRepository.save(clazz);

        classRegistrationRepository.save(classRegistrationEntity);

        return new ResponseEntity<>(new BasicResponse("补选成功！"+courseEntity.getName()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/classes/drop")
    @Authorization
    public ResponseEntity<BasicResponse> dropClass(@CurrentUser UserEntity user, @RequestBody AddClassRegistrationRequest request) {
        String userId = user.getUid();
        Long classId = request.getClassId();

        // Error 1: The class doesn't exist
        Optional<ClassEntity> classEntityOptional = classRepository.findById(classId);
        if (!classEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("找不到相应的教学班！"),
                    HttpStatus.BAD_REQUEST);
        }
        ClassEntity clazz = classEntityOptional.get();

        // Error 2: The user is not a student
        if (!user.readTypeName().equals("Student")) {  // The operator is not a student
            return new ResponseEntity<>(new BasicResponse("你不是学生，无法退课！"),
                    HttpStatus.FORBIDDEN);
        }
        String crid = user.getUid() + "CR"+classId;

        // Error 3: The class hasn't been registered
        Optional<ClassRegistrationEntity> classRegistrationEntityOptional = classRegistrationRepository.findByCrid(crid);
        if (!classRegistrationEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("你还未选上该课程！"),
                    HttpStatus.BAD_REQUEST);
        }
        ClassRegistrationEntity cr = classRegistrationEntityOptional.get();

        // Error 4: Status errors
        ClassStatusEnum status = cr.getStatus();
        if (status.equals(ClassStatusEnum.FINISHED) || status.equals(ClassStatusEnum.FAILED)) {
            return new ResponseEntity<>(new BasicResponse("课程已结束，无法退课！"),
                    HttpStatus.BAD_REQUEST);
        }

        // Error 5: Not in the drop time
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (!selectionTimeRepository.existsByStartLessThanEqualAndEndGreaterThanEqualAndDropTrue(currentTime, currentTime)) {
            return new ResponseEntity<>(new BasicResponse("现在不是退课时间！"),
                    HttpStatus.BAD_REQUEST);
        }

        classRegistrationRepository.delete(cr);
        clazz.setNumStudent(clazz.getNumStudent()-1);
        classRepository.save(clazz);

        return new ResponseEntity<>(new BasicResponse("退课成功！"), HttpStatus.OK);
    }

    // semester = "FIRST" / "SECOND"
    @GetMapping(path = "/classes/get_selected/{year}/{semester}")
    @Authorization
    public ResponseEntity<GetSelectedClassesResponse> getClassesTable(
            @CurrentUser UserEntity user,
            @PathVariable Integer year,
            @PathVariable SemesterEnum semester) {

        if (!user.readTypeName().equals("Student")) {
            return new ResponseEntity<>(new GetSelectedClassesResponse("你不是学生，无法显示课表！", new ArrayList<>()),
                    HttpStatus.FORBIDDEN);
        }

        List<ClassEntity> classesSelected = new ArrayList<>();
        List<ClassRegistrationEntity> classesRegistered = classRegistrationRepository.findByStudent(user);

        for (ClassRegistrationEntity cr : classesRegistered) {
            ClassEntity clazz = cr.getClazz();
            if (!clazz.getYear().equals(year) || !clazz.getSemester().equals(semester))
                continue;
            classesSelected.add(clazz);
        }
        return new ResponseEntity<>(new GetSelectedClassesResponse("课表显示成功！", classesSelected), HttpStatus.OK);
    }

    @PostMapping(path = "/classes/admin_register")
    @Authorization
    public ResponseEntity<BasicResponse> adminRegisterClass(@CurrentUser UserEntity user, @RequestBody ConfirmClassRequest request) {
        String studentId = request.getUid();
        long classId = request.getClassId();
        // Error 1: The class doesn't exist
        Optional<ClassEntity> classEntityOptional = classRepository.findById(classId);
        if (!classEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("找不到相应的教学班！"),
                    HttpStatus.BAD_REQUEST);
        }
        ClassEntity clazz = classEntityOptional.get();

        // Error 2: The user is not an admin
        if (!user.readTypeName().equals("System Administrator") &&
                !user.readTypeName().equals("Teaching Administrator")) {  // The operator is not a student
            return new ResponseEntity<>(new BasicResponse("你不是管理员！"),
                    HttpStatus.FORBIDDEN);
        }
        ClassStatusEnum classStatusEnum = ClassStatusEnum.SELECTED;
        String crid = user.getUid()+"CR"+classId;

        // Error 3: The object is not a student
        Optional<UserEntity> userEntityOptional = userRepository.findByUid(studentId);
        if (!userEntityOptional.isPresent() || !userEntityOptional.get().readTypeName().equals("Student")) {
            return new ResponseEntity<>(new BasicResponse("找不到该学生！"),
                    HttpStatus.BAD_REQUEST);
        }
        UserEntity student = userEntityOptional.get();

        // Error 4: The class has been registered
        if (classRegistrationRepository.existsByStudentAndClazz_Course(student, clazz.getCourse())) {
            return new ResponseEntity<>(new BasicResponse("该课程无法重复选择！"),
                    HttpStatus.BAD_REQUEST);
        }

        CourseEntity courseEntity = clazz.getCourse();

        // Error 5: The program_course doesn't exist
        if (!programCourseRepository.existsByCourseAndStudent(courseEntity, student)) {
            return new ResponseEntity<>(new BasicResponse("该课程在培养方案中不存在！"),
                    HttpStatus.BAD_REQUEST);
        }

        ClassRegistrationEntity classRegistrationEntity =
                new ClassRegistrationEntity(0, student, clazz, crid,
                        classStatusEnum, new Timestamp(System.currentTimeMillis()), null);

        // Error 6: Classroom is full of students
        if (classRegistrationRepository.countByClazz(clazz) >= clazz.getCapacity())
            return new ResponseEntity<>(new BasicResponse("该教学班人数已满！"),
                    HttpStatus.BAD_REQUEST);
        clazz.setNumStudent(classRegistrationRepository.countByClazz(clazz)+1);
        classRepository.save(clazz);

        classRegistrationRepository.save(classRegistrationEntity);

        return new ResponseEntity<>(new BasicResponse("管理员选课成功！"+courseEntity.getName()), HttpStatus.OK);
    }
}
