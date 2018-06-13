package tss.controllers;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.*;
import tss.exceptions.CourseNotFoundException;
import tss.exceptions.PermissionDeniedException;
import tss.exceptions.ProgramNotFoundException;
import tss.exceptions.UserNotStudentException;
import tss.repositories.*;
import tss.requests.information.*;
import tss.responses.information.*;

import java.util.*;

@Controller
@RequestMapping(path = "program")
public class ProgramController {
    private final ClassRegistrationRepository classRegistrationRepository;
    private final ProgramCourseRepository programCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public ProgramController(ClassRegistrationRepository classRegistrationRepository,
                             ProgramCourseRepository programCourseRepository, UserRepository userRepository,
                             CourseRepository courseRepository) {
        this.classRegistrationRepository = classRegistrationRepository;
        this.programCourseRepository = programCourseRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @PutMapping(path = "/course")
    @Authorization
    public ResponseEntity<BasicResponse> addCourseinProgram(@CurrentUser UserEntity user,
                                                                         @RequestBody AddCourseinProgramRequest request)
    {
        if (!user.readTypeName().equals("Student")) {
            throw new PermissionDeniedException();
        }

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(request.getCid());
        if (!courseEntityOptional.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("课程不存在！"), HttpStatus.FORBIDDEN);
        }

        CourseEntity courseEntity = courseEntityOptional.get();

        if (programCourseRepository.existsByCourseAndStudent(courseEntity, user))
        {
            return new ResponseEntity<>(new BasicResponse("该课程已存在！"), HttpStatus.FORBIDDEN);
        }
        ProgramCourseEntity programCourseEntity = new ProgramCourseEntity();

        // Modified by ljh
        MajorEntity major = user.getMajorClass().getMajor();
        Set<CourseEntity> coursesCompulsory = major.getSetOfCompulsory();
        Set<CourseEntity> coursesSelective = major.getSetOfSelective();
        Set<CourseEntity> coursesPublic = major.getSetOfPublic();

        if (coursesCompulsory.contains(courseEntity)) {
            programCourseEntity.setType(ProgramCourseEntity.COMPULSORY_COURSE);
        }
        else if (coursesSelective.contains(courseEntity)) {
            programCourseEntity.setType(ProgramCourseEntity.MAJOR_SELECTIVE_COURSE);
        }
        else if (coursesPublic.contains(courseEntity)) {
            programCourseEntity.setType(ProgramCourseEntity.PUBLIC_SELECTIVE_COURSE);
        }
        else
            return new ResponseEntity<>(new BasicResponse("专业计划中没有此课程！"), HttpStatus.FORBIDDEN);

        programCourseEntity.setCourse(courseEntity);
        programCourseEntity.setStudent(user);
        programCourseRepository.save(programCourseEntity);

        return new ResponseEntity<>(new BasicResponse("插入成功！"), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/course")
    @Authorization
    public ResponseEntity<DeleteCourseinProgramResponse> deleCourseinProgram(@CurrentUser UserEntity user,
                                                             @RequestBody DeleteCourseinProgramRequest request)
    {
        if (!user.readTypeName().equals("Student")) {
            throw new PermissionDeniedException();
        }

        String cid = request.getCid();
        Optional<CourseEntity> ret2 = courseRepository.findById(cid);
        if (!ret2.isPresent())
        {
            return new ResponseEntity<>(new DeleteCourseinProgramResponse("课程不存在！",
                    null,null,null), HttpStatus.BAD_REQUEST);
        }

        CourseEntity course = ret2.get();
        String cname = course.getName();

        Optional<ProgramCourseEntity> ret3 = programCourseRepository.findByCourseAndStudent(course, user);
        if (!ret3.isPresent())
        {
            return new ResponseEntity<>(new DeleteCourseinProgramResponse("此课程在培养方案中不存在，无法删除！",
                    cid,cname,user.getUid()), HttpStatus.BAD_REQUEST);
        }

        ProgramCourseEntity programcourse = ret3.get();

        programCourseRepository.delete(programcourse);

        return new ResponseEntity<>(new DeleteCourseinProgramResponse("ok", cid, cname, user.getUid()),
                HttpStatus.OK);
    }

    @GetMapping("/status")
    @Authorization
    public ResponseEntity<GetProgramCoursesResponse> getProgramCourses(@CurrentUser UserEntity user) {
        String uid = user.getUid();

        if (!user.readTypeName().equals("Student")) {
            throw new PermissionDeniedException();
        }

        MajorEntity major = user.getMajorClass().getMajor();
        Set<CourseEntity> coursesCompulsory = major.getSetOfCompulsory();
        Set<CourseEntity> coursesSelective = major.getSetOfSelective();
        Set<CourseEntity> coursesPublic = major.getSetOfPublic();

        List<CourseEntity> courses_final = new ArrayList<>();
        List<CourseTypeEnum> courses_type = new ArrayList<>();
        List<ClassStatusEnum> courses_status = new ArrayList<>();

        for (CourseEntity course : coursesCompulsory) {
            courses_final.add(course);
            courses_type.add(CourseTypeEnum.COMPULSORY);
            Optional<ClassRegistrationEntity> crs = classRegistrationRepository.findByStudentAndClazz_Course(user, course);
            if (crs.isPresent())
                courses_status.add(crs.get().getStatus());
            else {
                Optional<ProgramCourseEntity> programCourseEntityOptional =
                        programCourseRepository.findByCourseAndStudent(course, user);
                if (programCourseEntityOptional.isPresent()) {
                    courses_status.add(ClassStatusEnum.NOT_SELECTED);
                }
                else courses_status.add(ClassStatusEnum.NOT_IN_PROGRAM);
            }
        }

        for (CourseEntity course : coursesSelective) {
            courses_final.add(course);
            courses_type.add(CourseTypeEnum.SELECTIVE);
            Optional<ClassRegistrationEntity> crs = classRegistrationRepository.findByStudentAndClazz_Course(user, course);
            if (!crs.isPresent()) {
                Optional<ProgramCourseEntity> programCourseEntityOptional =
                        programCourseRepository.findByCourseAndStudent(course, user);
                if (programCourseEntityOptional.isPresent()) {
                    courses_status.add(ClassStatusEnum.NOT_SELECTED);
                }
                else courses_status.add(ClassStatusEnum.NOT_IN_PROGRAM);
            }
            else
                courses_status.add(crs.get().getStatus());
        }

        for (CourseEntity course : coursesPublic) {
            courses_final.add(course);
            courses_type.add(CourseTypeEnum.PUBLIC);
            Optional<ClassRegistrationEntity> crs = classRegistrationRepository.findByStudentAndClazz_Course(user, course);
            if (!crs.isPresent()) {
                Optional<ProgramCourseEntity> programCourseEntityOptional =
                        programCourseRepository.findByCourseAndStudent(course, user);
                if (programCourseEntityOptional.isPresent()) {
                    courses_status.add(ClassStatusEnum.NOT_SELECTED);
                }
                else courses_status.add(ClassStatusEnum.NOT_IN_PROGRAM);
            }
            else
                courses_status.add(crs.get().getStatus());
        }

        return new ResponseEntity<>(new GetProgramCoursesResponse("显示成功！",
                courses_final, courses_type, courses_status), HttpStatus.OK);
    }

    @GetMapping("/status/in_program")
    @Authorization
    public ResponseEntity<GetProgramCoursesResponse> getCoursesInProgram(@CurrentUser UserEntity user) {
        String uid = user.getUid();

        if (!user.readTypeName().equals("Student")) {
            throw new PermissionDeniedException();
        }

        MajorEntity major = user.getMajorClass().getMajor();
        Set<CourseEntity> coursesCompulsory = major.getSetOfCompulsory();
        Set<CourseEntity> coursesSelective = major.getSetOfSelective();
        Set<CourseEntity> coursesPublic = major.getSetOfPublic();

        List<CourseEntity> courses_final = new ArrayList<>();
        List<CourseTypeEnum> courses_type = new ArrayList<>();
        List<ClassStatusEnum> courses_status = new ArrayList<>();

        for (CourseEntity course : coursesCompulsory) {
            if (programCourseRepository.existsByCourseAndStudent(course, user)) {
                courses_final.add(course);
                courses_type.add(CourseTypeEnum.COMPULSORY);
                Optional<ClassRegistrationEntity> crs = classRegistrationRepository.findByStudentAndClazz_Course(user, course);
                if (crs.isPresent()) {
                    courses_status.add(crs.get().getStatus());
                }
                else {
                    courses_status.add(ClassStatusEnum.NOT_SELECTED);
                }
            }
        }

        for (CourseEntity course : coursesSelective) {
            if (programCourseRepository.existsByCourseAndStudent(course, user)) {
                courses_final.add(course);
                courses_type.add(CourseTypeEnum.SELECTIVE);
                Optional<ClassRegistrationEntity> crs = classRegistrationRepository.findByStudentAndClazz_Course(user, course);
                if (crs.isPresent()) {
                    courses_status.add(crs.get().getStatus());
                }
                else {
                    courses_status.add(ClassStatusEnum.NOT_SELECTED);
                }
            }
        }

        for (CourseEntity course : coursesPublic) {
            if (programCourseRepository.existsByCourseAndStudent(course, user)) {
                courses_final.add(course);
                courses_type.add(CourseTypeEnum.PUBLIC);
                Optional<ClassRegistrationEntity> crs = classRegistrationRepository.findByStudentAndClazz_Course(user, course);
                if (crs.isPresent()) {
                    courses_status.add(crs.get().getStatus());
                }
                else {
                    courses_status.add(ClassStatusEnum.NOT_SELECTED);
                }
            }
        }

        return new ResponseEntity<>(new GetProgramCoursesResponse("显示成功！",
                courses_final, courses_type, courses_status), HttpStatus.OK);
    }

    @GetMapping("/status/not_in_program")
    @Authorization
    public ResponseEntity<GetProgramCoursesResponse> getCoursesNotInProgram(@CurrentUser UserEntity user) {
        String uid = user.getUid();

        if (!user.readTypeName().equals("Student")) {
            throw new PermissionDeniedException();
        }

        MajorEntity major = user.getMajorClass().getMajor();
        Set<CourseEntity> coursesCompulsory = major.getSetOfCompulsory();
        Set<CourseEntity> coursesSelective = major.getSetOfSelective();
        Set<CourseEntity> coursesPublic = major.getSetOfPublic();

        List<CourseEntity> courses_final = new ArrayList<>();
        List<CourseTypeEnum> courses_type = new ArrayList<>();
        List<ClassStatusEnum> courses_status = new ArrayList<>();

        for (CourseEntity course : coursesCompulsory) {
            if (programCourseRepository.existsByCourseAndStudent(course, user)) continue;
            courses_final.add(course);
            courses_type.add(CourseTypeEnum.COMPULSORY);
            courses_status.add(ClassStatusEnum.NOT_IN_PROGRAM);
        }

        for (CourseEntity course : coursesSelective) {
            if (programCourseRepository.existsByCourseAndStudent(course, user)) continue;
            courses_final.add(course);
            courses_type.add(CourseTypeEnum.SELECTIVE);
            courses_status.add(ClassStatusEnum.NOT_IN_PROGRAM);
        }

        for (CourseEntity course : coursesPublic) {
            if (programCourseRepository.existsByCourseAndStudent(course, user)) continue;
            courses_final.add(course);
            courses_type.add(CourseTypeEnum.PUBLIC);
            courses_status.add(ClassStatusEnum.NOT_IN_PROGRAM);
        }

        return new ResponseEntity<>(new GetProgramCoursesResponse("显示成功！", courses_final, courses_type, courses_status), HttpStatus.OK);
    }
}
