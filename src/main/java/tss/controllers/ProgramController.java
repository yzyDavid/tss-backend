package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.ProgramCourseEntity;
import tss.entities.UserEntity;
import tss.repositories.CourseRepository;
import tss.repositories.ProgramCourseRepository;
import tss.repositories.ProgramRepository;
import tss.repositories.UserRepository;
import tss.requests.information.AddCourseinProgramRequest;
import tss.responses.information.AddCourseinProgramResponse;
import tss.responses.information.AddDepartmentResponse;

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

    @PutMapping(path = "add")
    @Authorization
    public ResponseEntity<AddCourseinProgramResponse> addCourseinProgram(@CurrentUser UserEntity user,
                                                                         @RequestBody AddCourseinProgramRequest request)
    {

        if (!user.getName().equals(request.getStudentname()))
        {
            return new ResponseEntity<>(new AddCourseinProgramResponse("permission denied"), HttpStatus.FORBIDDEN);
        } // TODO: Duplicate insert detection
        ProgramCourseEntity programcourse = new ProgramCourseEntity();

        // TODO: if coursename do not exist
        programcourse.setType(request.getCoursetype());
        programcourse.setCourse(courseRepository.findByName(request.getCoursename()).get(0));
        programcourse.setProgram(programRepository.findByStudents(user).get(0));
        programCourseRepository.save(programcourse);


        return new ResponseEntity<>(new AddCourseinProgramResponse("ok"), HttpStatus.CREATED);

    }

}
