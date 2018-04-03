package tss.information;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.session.Authorization;
import tss.session.CurrentUser;

import java.util.*;

@Controller
@RequestMapping(path = "/course")
public class CourseController {
    private CourseRepository courseRepository;
    private TeachesRepository teachesRepository;
    private UserRepository userRepository;
    private TakesRepository takesRepository;

    @Autowired
    CourseController(CourseRepository courseRepository, TeachesRepository teachesRepository,
                     TakesRepository takesRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.teachesRepository = teachesRepository;
        this.takesRepository = takesRepository;
        this.userRepository = userRepository;
    }

    @PutMapping(path = "/add")
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

    @DeleteMapping(path = "/delete")
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

        return new ResponseEntity<>(new DeleteCourseResponse("ok", course.getCid(), course.getName()), HttpStatus.OK);

    }


    @PostMapping(path = "/info")
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

        return new ResponseEntity<>(new ModifyCourseResponse("ok", "", ""), HttpStatus.OK);

    }

    @GetMapping(path = "/info")
    public ResponseEntity<GetCourseResponse> getInfo(@RequestBody GetCourseRequest request) {
        String cid = request.getCid();
        if(!courseRepository.existsById(cid))
            return new ResponseEntity<>(new GetCourseResponse("course non-exist", "", "", 0.0f,
                    null, 0, ""), HttpStatus.BAD_REQUEST);

        CourseEntity course = courseRepository.findById(cid).get();
        return new ResponseEntity<>(new GetCourseResponse("ok", course.getCid(), course.getName(),
                course.getCredit(), course.getSemester(), course.getCapacity(), course.getIntro()), HttpStatus.OK);

    }

    @GetMapping(path = "/instructor")
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
        for(TeachesEntity instructor : course.getTeaches()) {
            UserEntity tmp = instructor.getTeacher();
            tids.add(tmp.getUid());
            names.add(tmp.getName());
            dates.add(instructor.getDate());
            begintimes.add(instructor.getBeginTime());
            durations.add(instructor.getDuration());
            classrooms.add(instructor.getClassroom());
        }
        return new ResponseEntity<>(new GetInstructorsResponse("ok", tids, names, dates, begintimes,
                durations, classrooms), HttpStatus.OK);

    }

    @PutMapping(path = "/instructor")
    @Authorization
    public ResponseEntity<AddInstructorsResponse> addInstructors(@CurrentUser UserEntity user,
                                                        @RequestBody AddInstructorsRequest request) {
        String cid = request.getCid();
        Set<String> uids = request.getUids();
        if(!courseRepository.existsById(cid))
            return new ResponseEntity<>(new AddInstructorsResponse("course doesn't exist", uids), HttpStatus.BAD_REQUEST);
        else if(user.getType() != UserEntity.TYPE_MANAGER)
            return new ResponseEntity<>(new AddInstructorsResponse("permission denied", uids), HttpStatus.FORBIDDEN);

        Set<String> fail = new HashSet<>();
        for(String uid : uids)
            if(!userRepository.existsById(uid))
                fail.add(uid);
        if(!fail.isEmpty())
            return new ResponseEntity<>(new AddInstructorsResponse("uids don't exist", fail), HttpStatus.BAD_REQUEST);

        for(String uid : uids)
            if(userRepository.findById(uid).get().getType() != UserEntity.TYPE_TEACHER)
                fail.add(uid);
        if(!fail.isEmpty())
            return new ResponseEntity<>(new AddInstructorsResponse("users are not teachers", fail), HttpStatus.BAD_REQUEST);

        CourseEntity course = courseRepository.findById(cid).get();
        for(String uid : uids) {
            TeachesEntity teachesEntity = new TeachesEntity(userRepository.findById(uid).get(), course);
            course.addTeaches(teachesEntity);
        }
        return new ResponseEntity<>(new AddInstructorsResponse("OK", null), HttpStatus.OK);
    }

    @DeleteMapping(path = "/instructor")
    @Authorization
    public ResponseEntity<DeleteInstructorsResponse> deleteInstructors(@CurrentUser UserEntity user,
                                                                 @RequestBody DeleteInstructorsRequest request) {
        String cid = request.getCid();
        Set<String> uids = request.getUids();
        if(!courseRepository.existsById(cid))
            return new ResponseEntity<>(new DeleteInstructorsResponse("course doesn't exist"), HttpStatus.BAD_REQUEST);
        else if(user.getType() != UserEntity.TYPE_MANAGER)
            return new ResponseEntity<>(new DeleteInstructorsResponse("permission denied"), HttpStatus.FORBIDDEN);

        CourseEntity course = courseRepository.findById(cid).get();
        course.deleteTeaches(uids);
        return new ResponseEntity<>(new DeleteInstructorsResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = "/instructor")
    @Authorization
    public ResponseEntity<ModifyInstructorResponse> modifyInstructors(@CurrentUser UserEntity user,
                                                                       @RequestBody ModifyInstructorRequest request) {
        String cid = request.getCid();
        String uid = request.getUid();
        if(user.getType() != UserEntity.TYPE_MANAGER || user.getUid() != uid)
            return new ResponseEntity<>(new ModifyInstructorResponse("permission denied"), HttpStatus.FORBIDDEN);
        else if(!courseRepository.existsById(cid))
            return new ResponseEntity<>(new ModifyInstructorResponse("course doesn't exist"), HttpStatus.BAD_REQUEST);

        CourseEntity course = courseRepository.findById(cid).get();
        TeachesEntity instructor = course.findTeachesByUid(uid);
        if(instructor == null)
            return new ResponseEntity<>(new ModifyInstructorResponse("Instructor doesn't exist"), HttpStatus.BAD_REQUEST);
        instructor.setDate(request.getDate());
        instructor.setClassroom(request.getClassroom());
        instructor.setBeginTime(request.getBeginTime());
        instructor.setDuration(request.getDuration());

        return new ResponseEntity<>(new ModifyInstructorResponse("OK"), HttpStatus.OK);
    }

    @PutMapping(path = "/student")
    @Authorization
    public ResponseEntity<AddStudentsResponse> addStudents(@CurrentUser UserEntity user,
                                                                 @RequestBody AddStudentsRequest request) {
        String cid = request.getCid();
        String tid = request.getTid();
        Set<String> sids = request.getSids();

        if(user.getType() != UserEntity.TYPE_MANAGER)
            return new ResponseEntity<>(new AddStudentsResponse("permission denied", sids), HttpStatus.FORBIDDEN);
        else if(!courseRepository.existsById(cid))
            return new ResponseEntity<>(new AddStudentsResponse("course doesn't exist", sids), HttpStatus.BAD_REQUEST);

        TeachesEntity instructor = courseRepository.findById(cid).get().findTeachesByUid(tid);
        if(instructor == null)
            return new ResponseEntity<>(new AddStudentsResponse("Instructor doesn't exist", sids), HttpStatus.BAD_REQUEST);

        Set<String> fail = new HashSet<>();
        for(String uid : sids)
            if(!userRepository.existsById(uid))
                fail.add(uid);
        if(!fail.isEmpty())
            return new ResponseEntity<>(new AddStudentsResponse("uids don't exist", fail), HttpStatus.BAD_REQUEST);

        for(String uid : sids)
            if(userRepository.findById(uid).get().getType() != UserEntity.TYPE_STUDENT)
                fail.add(uid);
        if(!fail.isEmpty())
            return new ResponseEntity<>(new AddStudentsResponse("users are not students", fail), HttpStatus.BAD_REQUEST);




        for(String uid : sids) {
            TakesEntity tmp = new TakesEntity();
            //tmp.setYear(curYear); TODO: keep school year
            instructor.addTake(tmp);
        }

        return new ResponseEntity<>(new AddStudentsResponse("OK", null), HttpStatus.OK);
    }

    @DeleteMapping(path = "/student")
    @Authorization
    public ResponseEntity<DeleteStudentsResponse> deleteStudents(@CurrentUser UserEntity user,
                                                                       @RequestBody DeleteStudentsRequest request) {
        String cid = request.getCid();
        String tid = request.getTid();
        Set<String> uids = request.getSid();
        if(user.getType() != UserEntity.TYPE_MANAGER)
            return new ResponseEntity<>(new DeleteStudentsResponse("permission denied"), HttpStatus.FORBIDDEN);
        else if(!courseRepository.existsById(cid))
            return new ResponseEntity<>(new DeleteStudentsResponse("course doesn't exist"), HttpStatus.BAD_REQUEST);

        TeachesEntity instructor = courseRepository.findById(cid).get().findTeachesByUid(tid);
        if(instructor == null)
            return new ResponseEntity<>(new DeleteStudentsResponse("Instructor doesn't exist"), HttpStatus.BAD_REQUEST);

        instructor.deleteTakes(uids);
        return new ResponseEntity<>(new DeleteStudentsResponse("OK"), HttpStatus.OK);
    }




}
