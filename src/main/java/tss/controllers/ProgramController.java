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
    private final ProgramRepository programRepository;
    private final ProgramCourseRepository programCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public ProgramController(ClassRegistrationRepository classRegistrationRepository, ProgramRepository programRepository,
                             ProgramCourseRepository programCourseRepository, UserRepository userRepository,
                             CourseRepository courseRepository) {
        this.classRegistrationRepository = classRegistrationRepository;
        this.programRepository = programRepository;
        this.programCourseRepository = programCourseRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @PutMapping
    @Authorization
    public ResponseEntity<AddProgramResponse> addProgram(@CurrentUser UserEntity user,
                                                         @RequestBody AddProgramRequest request)
    {

        if (!user.readTypeName().equals("Student")) {
            return new ResponseEntity<>(new AddProgramResponse("Permission denied: You are not a student"), HttpStatus.FORBIDDEN);
        }

        ProgramEntity program = new ProgramEntity();

        program.setUid(user.getUid());
        program.setPid(user.getUid());
        UserEntity user_i = userRepository.findById(user.getUid()).get();
        HashSet<UserEntity> users = new HashSet<>();
        users.add(user_i);
        program.setStudents(users);
        programRepository.save(program);

        return new ResponseEntity<>(new AddProgramResponse("ok"), HttpStatus.CREATED);

    }

    @PutMapping(path = "course")
    @Authorization
    public ResponseEntity<AddCourseinProgramResponse> addCourseinProgram(@CurrentUser UserEntity user,
                                                                         @RequestBody AddCourseinProgramRequest request)
    {
        if (!user.readTypeName().equals("Student")) {
            return new ResponseEntity<>(new AddCourseinProgramResponse("Permission denied: You are not a student"), HttpStatus.FORBIDDEN);
        }

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(request.getCid());
        if (!courseEntityOptional.isPresent()) {
            return new ResponseEntity<>(new AddCourseinProgramResponse("no such course"), HttpStatus.FORBIDDEN);
        }
        Optional<ProgramEntity> programEntityOptional = programRepository.findByPid(user.getUid());
        if (!programEntityOptional.isPresent()) {
            return new ResponseEntity<>(new AddCourseinProgramResponse("no such student"), HttpStatus.FORBIDDEN);
        }

        CourseEntity courseEntity = courseEntityOptional.get();
        ProgramEntity programEntity = programEntityOptional.get();

        if (programCourseRepository.existsByCourse(courseEntity)
                && programCourseRepository.existsByProgram(programEntity))
        {
            return new ResponseEntity<>(new AddCourseinProgramResponse("duplicate insert"), HttpStatus.FORBIDDEN);
        }
        ProgramCourseEntity programcourse = new ProgramCourseEntity();

        // Modified by ljh
        MajorEntity major = user.getMajorClass().getMajor();
        Set<CourseEntity> coursesCompulsory = major.getSetOfCompulsory();
        Set<CourseEntity> coursesSelective = major.getSetOfSelective();
        Set<CourseEntity> coursesPublic = major.getSetOfPublic();

        if (coursesCompulsory.contains(courseEntity)) {
            programcourse.setType(ProgramCourseEntity.COMPULSORY_COURSE);
        }
        else if (coursesSelective.contains(courseEntity)) {
            programcourse.setType(ProgramCourseEntity.MAJOR_SELECTIVE_COURSE);
        }
        else if (coursesPublic.contains(courseEntity)) {
            programcourse.setType(ProgramCourseEntity.PUBLIC_SELECTIVE_COURSE);
        }
        else
            return new ResponseEntity<>(new AddCourseinProgramResponse("course not in major programs"), HttpStatus.FORBIDDEN);

        programcourse.setCourse(courseEntity);
        programcourse.setProgram(programEntity);
        programCourseRepository.save(programcourse);

        return new ResponseEntity<>(new AddCourseinProgramResponse("ok"), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/course")
    @Authorization
    public ResponseEntity<DeleteCourseinProgramResponse> deleCourseinProgram(@CurrentUser UserEntity user,
                                                             @RequestBody DeleteCourseinProgramRequest request)
    {
        if (!user.readTypeName().equals("Student")) {
            return new ResponseEntity<>(new DeleteCourseinProgramResponse("Permission denied: You are not a student",
                    null, null, null), HttpStatus.FORBIDDEN);
        }

        String pid = user.getUid();
        String cid = request.getCid();

        Optional<ProgramEntity> ret = programRepository.findByPid(pid);
        if (!ret.isPresent())
        {
            return new ResponseEntity<>(new DeleteCourseinProgramResponse("non-existent program id",
                    null,null,null), HttpStatus.BAD_REQUEST);
        }

        Optional<CourseEntity> ret2 = courseRepository.findById(cid);
        if (!ret2.isPresent())
        {
            return new ResponseEntity<>(new DeleteCourseinProgramResponse("non-existent course id",
                    null,null,null), HttpStatus.BAD_REQUEST);
        }


        ProgramEntity program = ret.get();
        CourseEntity course = ret2.get();
        String cname = course.getName();

        Optional<ProgramCourseEntity> ret3 = programCourseRepository.findByCourseAndProgram(course, program);
        if (!ret3.isPresent())
        {
            return new ResponseEntity<>(new DeleteCourseinProgramResponse("this course is not in the program",cid,cname,pid), HttpStatus.BAD_REQUEST);
        }

        ProgramCourseEntity programcourse = ret3.get();

        programCourseRepository.delete(programcourse);

        return new ResponseEntity<>(new DeleteCourseinProgramResponse("ok", cid, cname,pid), HttpStatus.OK);
    }

    @GetMapping("/status")
    @Authorization
    public ResponseEntity<GetProgramCoursesResponse> getProgramCourses(@CurrentUser UserEntity user) {
        String uid = user.getUid();

        if (!user.readTypeName().equals("Student")) {
            throw new PermissionDeniedException();
        }

        Optional<ProgramEntity> programEntity = programRepository.findByPid(user.getUid());
        if (!programEntity.isPresent()) {
            throw new ProgramNotFoundException();
        }
        ProgramEntity program = programEntity.get();

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
                Optional<ProgramCourseEntity> programCourseEntityOptional = programCourseRepository.findByCourseAndProgram(course, program);
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
                Optional<ProgramCourseEntity> programCourseEntityOptional = programCourseRepository.findByCourseAndProgram(course, program);
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
                Optional<ProgramCourseEntity> programCourseEntityOptional = programCourseRepository.findByCourseAndProgram(course, program);
                if (programCourseEntityOptional.isPresent()) {
                    courses_status.add(ClassStatusEnum.NOT_SELECTED);
                }
                else courses_status.add(ClassStatusEnum.NOT_IN_PROGRAM);
            }
            else
                courses_status.add(crs.get().getStatus());
        }

        return new ResponseEntity<>(new GetProgramCoursesResponse(courses_final, courses_type, courses_status), HttpStatus.OK);
    }

    @GetMapping("/status/in_program")
    @Authorization
    public ResponseEntity<GetProgramCoursesResponse> getCoursesInProgram(@CurrentUser UserEntity user) {
        String uid = user.getUid();

        if (!user.readTypeName().equals("Student")) {
            throw new PermissionDeniedException();
        }

        Optional<ProgramEntity> programEntity = programRepository.findByPid(user.getUid());
        if (!programEntity.isPresent()) {
            throw new ProgramNotFoundException();
        }
        ProgramEntity program = programEntity.get();

        MajorEntity major = user.getMajorClass().getMajor();
        Set<CourseEntity> coursesCompulsory = major.getSetOfCompulsory();
        Set<CourseEntity> coursesSelective = major.getSetOfSelective();
        Set<CourseEntity> coursesPublic = major.getSetOfPublic();

        List<CourseEntity> courses_final = new ArrayList<>();
        List<CourseTypeEnum> courses_type = new ArrayList<>();
        List<ClassStatusEnum> courses_status = new ArrayList<>();

        for (CourseEntity course : coursesCompulsory) {
            if (programCourseRepository.existsByCourseAndProgram(course, program)) {
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
            if (programCourseRepository.existsByCourseAndProgram(course, program)) {
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
            if (programCourseRepository.existsByCourseAndProgram(course, program)) {
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

        return new ResponseEntity<>(new GetProgramCoursesResponse(courses_final, courses_type, courses_status), HttpStatus.OK);
    }

    @GetMapping("/status/not_in_program")
    @Authorization
    public ResponseEntity<GetProgramCoursesResponse> getCoursesNotInProgram(@CurrentUser UserEntity user) {
        String uid = user.getUid();

        if (!user.readTypeName().equals("Student")) {
            throw new PermissionDeniedException();
        }

        Optional<ProgramEntity> programEntity = programRepository.findByPid(user.getUid());
        if (!programEntity.isPresent()) {
            throw new ProgramNotFoundException();
        }
        ProgramEntity program = programEntity.get();

        MajorEntity major = user.getMajorClass().getMajor();
        Set<CourseEntity> coursesCompulsory = major.getSetOfCompulsory();
        Set<CourseEntity> coursesSelective = major.getSetOfSelective();
        Set<CourseEntity> coursesPublic = major.getSetOfPublic();

        List<CourseEntity> courses_final = new ArrayList<>();
        List<CourseTypeEnum> courses_type = new ArrayList<>();
        List<ClassStatusEnum> courses_status = new ArrayList<>();

        for (CourseEntity course : coursesCompulsory) {
            if (programCourseRepository.existsByCourseAndProgram(course, program)) continue;
            courses_final.add(course);
            courses_type.add(CourseTypeEnum.COMPULSORY);
            courses_status.add(ClassStatusEnum.NOT_IN_PROGRAM);
        }

        for (CourseEntity course : coursesSelective) {
            if (programCourseRepository.existsByCourseAndProgram(course, program)) continue;
            courses_final.add(course);
            courses_type.add(CourseTypeEnum.SELECTIVE);
            courses_status.add(ClassStatusEnum.NOT_IN_PROGRAM);
        }

        for (CourseEntity course : coursesPublic) {
            if (programCourseRepository.existsByCourseAndProgram(course, program)) continue;
            courses_final.add(course);
            courses_type.add(CourseTypeEnum.PUBLIC);
            courses_status.add(ClassStatusEnum.NOT_IN_PROGRAM);
        }

        return new ResponseEntity<>(new GetProgramCoursesResponse(courses_final, courses_type, courses_status), HttpStatus.OK);
    }
}
