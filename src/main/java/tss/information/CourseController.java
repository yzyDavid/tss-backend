package tss.information;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.session.Authorization;
import tss.session.CurrentUser;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/course")
public class CourseController {
    private CourseRepository courseRepository;
    private InstructorRepository instructorRepository;
    private TakesRepository takesRepository;

    @Autowired
    CourseController(CourseRepository courseRepository, InstructorRepository instructorRepository,
                     TakesRepository takesRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.takesRepository = takesRepository;
    }

    @PutMapping(path = "")
    @Authorization
    public ResponseEntity<AddCourseResponse> addCourse(@CurrentUser UserEntity user,
                                                   @RequestBody AddCourseRequest request) {
        String cid = request.getCid();
        if(courseRepository.existsById(cid))
            return new ResponseEntity<>(new AddCourseResponse("failed with duplicated cid", "", ""), HttpStatus.BAD_REQUEST);
        else if(user.getType() != UserEntity.TYPE_MANAGER)
            return new ResponseEntity<>(new AddCourseResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);

        CourseEntity course = new CourseEntity();
        course.setCid(request.getCid());
        course.setName(request.getName());
        course.setCapacity(request.getCapacity());
        course.setCredit(request.getCredit());
        course.setSemester(request.getSemester());
        courseRepository.save(course);

        return new ResponseEntity<>(new AddCourseResponse("ok", course.getCid(), course.getName()), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "")
    @Authorization
    public ResponseEntity<DeleteCourseResponse> deleteCourse(@CurrentUser UserEntity user,
                                                       @RequestBody DeleteCourseRequest request) {
        String cid = request.getCid();
        if(!courseRepository.existsById(cid))
            return new ResponseEntity<>(new DeleteCourseResponse("course non-exist", "", ""), HttpStatus.BAD_REQUEST);
        else if(user.getType() != UserEntity.TYPE_MANAGER)
            return new ResponseEntity<>(new DeleteCourseResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);

        CourseEntity course = courseRepository.findById(cid).get();
        courseRepository.delete(course);

        return new ResponseEntity<>(new DeleteCourseResponse("ok", course.getCid(), course.getName()), HttpStatus.NO_CONTENT);

    }


    @PostMapping(path = "")
    @Authorization
    public ResponseEntity<ModifyCourseResponse> modifyInfo(@CurrentUser UserEntity user,
                                                       @RequestBody ModifyCourseRequest request) {
        String cid = request.getCid();
        if(courseRepository.existsById(cid))
            return new ResponseEntity<>(new ModifyCourseResponse("failed with duplicated cid", "", ""), HttpStatus.BAD_REQUEST);
        else if(user.getType() != UserEntity.TYPE_MANAGER)
            return new ResponseEntity<>(new ModifyCourseResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
        CourseEntity course = courseRepository.findById(cid).get();
        course.setCid(request.getCid());
        course.setName(request.getName());
        course.setCapacity(request.getCapacity());
        course.setCredit(request.getCredit());
        course.setSemester(request.getSemester());
        course.setIntro(request.getIntro());
        courseRepository.save(course);

        return new ResponseEntity<>(new ModifyCourseResponse("ok", "", ""), HttpStatus.CREATED);

    }

    @PostMapping(path = "")
    public ResponseEntity<GetCourseResponse> getInfo(@RequestBody GetCourseRequest request) {
        String cid = request.getCid();
        if(!courseRepository.existsById(cid))
            return new ResponseEntity<>(new GetCourseResponse("course non-exist", "", "", 0,
                    0, 0, ""), HttpStatus.BAD_REQUEST);

        CourseEntity course = courseRepository.findById(cid).get();
        return new ResponseEntity<>(new GetCourseResponse("ok", course.getCid(), course.getName(),
                course.getCredit(), course.getSemester(), course.getCapacity(), course.getIntro()), HttpStatus.CREATED);

    }

    @PostMapping(path = "")
    public ResponseEntity<GetInstructorsResponse> getInstructors(@RequestBody GetInstructorsRequest request) {
        String cid = request.getCid();
        if(!courseRepository.existsById(cid))
            return new ResponseEntity<>(new GetInstructorsResponse("course non-exist", null, null,
                    null, null, null, null), HttpStatus.BAD_REQUEST);

        CourseEntity course = courseRepository.findById(cid).get();
        List<String> tids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Integer> dates = new ArrayList<>();
        List<Integer> begintimes = new ArrayList<>();
        List<Integer> durations = new ArrayList<>();
        List<String> classrooms = new ArrayList<>();
        for(InstructorEntity instructor : course.getInstructors()) {
            UserEntity tmp = instructor.getTeacher();
            tids.add(tmp.getUid());
            names.add(tmp.getName());
            dates.add(instructor.getDate());
            begintimes.add(instructor.getBeginTime());
            durations.add(instructor.getDuration());
            classrooms.add(instructor.getClassroom());
        }
        return new ResponseEntity<>(new GetInstructorsResponse("ok", tids, names, dates, begintimes,
                durations, classrooms), HttpStatus.CREATED);

    }


}
