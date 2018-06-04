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

        if (!user.getUid().equals(request.getUid()))
        {
            return new ResponseEntity<>(new AddProgramResponse("permission denied"), HttpStatus.FORBIDDEN);
        }
        ProgramEntity program = new ProgramEntity();

        program.setUid(request.getUid());
        program.setPid(request.getUid());
        UserEntity user_i = userRepository.findById(request.getUid()).get();
        HashSet<UserEntity> users = new HashSet<>();
        users.add(user_i);
        program.setStudents(users);
        programRepository.save(program);

        return new ResponseEntity<>(new AddProgramResponse("ok"), HttpStatus.CREATED);

    }

    @PutMapping(path = "course")
//    @Authorization
    public ResponseEntity<AddCourseinProgramResponse> addCourseinProgram(//@CurrentUser UserEntity user,
                                                                         @RequestBody AddCourseinProgramRequest request)
    {

/*        if (!user.getUid().equals(request.getUid()))
        {
            return new ResponseEntity<>(new AddCourseinProgramResponse("permission denied"), HttpStatus.FORBIDDEN);
        }*/

        CourseEntity course = courseRepository.findById(request.getCid()).get();
        ProgramEntity program = programRepository.findByPid(request.getPid()).get();
        if (course == null) {
            return new ResponseEntity<>(new AddCourseinProgramResponse("no such course"), HttpStatus.FORBIDDEN);
        } else if (program == null) {
            return new ResponseEntity<>(new AddCourseinProgramResponse("no such student"), HttpStatus.FORBIDDEN);
        }

        if (programCourseRepository.existsByCourse(course) && programCourseRepository.existsByProgram(program))
        {
            return new ResponseEntity<>(new AddCourseinProgramResponse("duplicate insert"), HttpStatus.FORBIDDEN);
        }
        ProgramCourseEntity programcourse = new ProgramCourseEntity();


        programcourse.setType(request.getType());
        programcourse.setCourse(courseRepository.findById(request.getCid()).get());
        programcourse.setProgram(programRepository.findByPid(request.getPid()).get());
        programCourseRepository.save(programcourse);


        return new ResponseEntity<>(new AddCourseinProgramResponse("ok"), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/course")
 //   @Authorization
    public ResponseEntity<DeleteCourseinProgramResponse> deleCourseinProgram(//@CurrentUser UserEntity user,
                                                             @RequestBody DeleteCourseinProgramRequest request)
    {
/*        if (!user.getUid().equals(request.getPid()))
        {
            return new ResponseEntity<>(new DeleteCourseinProgramResponse("permission denied",null,null,null), HttpStatus.FORBIDDEN);
        }*/
        String pid = request.getPid();
        String cid = request.getCid();

        Optional<ProgramEntity> ret = programRepository.findByPid(pid);
        if (!ret.isPresent())
        {
            return new ResponseEntity<>(new DeleteCourseinProgramResponse("non-existent program id",null,null,null), HttpStatus.BAD_REQUEST);
        }

        Optional<CourseEntity> ret2 = courseRepository.findById(cid);
        if (!ret2.isPresent())
        {
            return new ResponseEntity<>(new DeleteCourseinProgramResponse("non-existent course id",null,null,null), HttpStatus.BAD_REQUEST);
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
            throw new UserNotStudentException();
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
            if (!crs.isPresent())
                courses_status.add(ClassStatusEnum.NOT_SELECTED);
            else
                courses_status.add(crs.get().getStatus());
        }

        for (CourseEntity course : coursesSelective) {
            courses_final.add(course);
            courses_type.add(CourseTypeEnum.SELECTIVE);
            Optional<ClassRegistrationEntity> crs = classRegistrationRepository.findByStudentAndClazz_Course(user, course);
            if (!crs.isPresent())
                courses_status.add(ClassStatusEnum.NOT_SELECTED);
            else
                courses_status.add(crs.get().getStatus());
        }

        for (CourseEntity course : coursesPublic) {
            courses_final.add(course);
            courses_type.add(CourseTypeEnum.PUBLIC);
            Optional<ClassRegistrationEntity> crs = classRegistrationRepository.findByStudentAndClazz_Course(user, course);
            if (!crs.isPresent())
                courses_status.add(ClassStatusEnum.NOT_SELECTED);
            else
                courses_status.add(crs.get().getStatus());
        }

        return new ResponseEntity<>(new GetProgramCoursesResponse(courses_final, courses_type, courses_status), HttpStatus.OK);
    }

}
