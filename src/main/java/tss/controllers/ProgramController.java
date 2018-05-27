package tss.controllers;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.CourseEntity;
import tss.entities.ProgramCourseEntity;
import tss.entities.ProgramEntity;
import tss.entities.UserEntity;
import tss.repositories.CourseRepository;
import tss.repositories.ProgramCourseRepository;
import tss.repositories.ProgramRepository;
import tss.repositories.UserRepository;
import tss.requests.information.*;
import tss.responses.information.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(path = "program")
public class ProgramController {
    private final ProgramRepository programRepository;
    private final ProgramCourseRepository programCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public ProgramController(ProgramRepository programRepository, UserRepository userRepository,
                             CourseRepository courseRepository, ProgramCourseRepository programCourseRepository)
    {
        this.programRepository = programRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.programCourseRepository = programCourseRepository;
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
}
