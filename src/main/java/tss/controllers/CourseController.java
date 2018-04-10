package tss.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.entities.*;
import tss.repositories.*;
import tss.requests.information.*;
import tss.responses.information.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;

import java.util.Optional;

/**
 * @author Mingqi Yi
 */
@Controller
@RequestMapping(path = "/course")
public class CourseController {
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;


    @Autowired
    CourseController(CourseRepository courseRepository, DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
    }

    @PutMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddCourseResponse> addCourse(@CurrentUser UserEntity user,
                                                       @RequestBody AddCourseRequest request) {
        String cid = request.getCid();
        if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new AddCourseResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
        } else if (courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new AddCourseResponse("failed with duplicated cid", null, null), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity dept = null;
        if(request.getDept() != null) {
            Optional<DepartmentEntity> ret = departmentRepository.findByName(request.getDept());
            if(!ret.isPresent()) {
                return new ResponseEntity<>(new AddCourseResponse("Department doesn't exist", null, null), HttpStatus.BAD_REQUEST);
            }
            dept = ret.get();
        }
        CourseEntity course = new CourseEntity();
        course.setCid(request.getCid());
        course.setName(request.getName());
        course.setCredit(request.getCredit());
        course.setSemester(request.getSemester());
        course.setDepartment(dept);
        courseRepository.save(course);

        return new ResponseEntity<>(new AddCourseResponse("ok", course.getCid(), course.getName()), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteCourseResponse> deleteCourse(@CurrentUser UserEntity user,
                                                             @RequestBody DeleteCourseRequest request) {
        String cid = request.getCid();
        if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new DeleteCourseResponse("course non-exist"), HttpStatus.BAD_REQUEST);
        } else if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new DeleteCourseResponse("permission denied"), HttpStatus.FORBIDDEN);
        }

        courseRepository.deleteById(cid);

        return new ResponseEntity<>(new DeleteCourseResponse("ok"), HttpStatus.OK);

    }


    @PostMapping(path = "/info")
    @Authorization
    public ResponseEntity<ModifyCourseResponse> modifyInfo(@CurrentUser UserEntity user,
                                                           @RequestBody ModifyCourseRequest request) {
        String cid = request.getCid();
        if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new ModifyCourseResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
        }
        Optional<CourseEntity> ret = courseRepository.findById(cid);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyCourseResponse("course doesn't exist", null, null), HttpStatus.BAD_REQUEST);
        }
        CourseEntity course = ret.get();
        DepartmentEntity dept = null;
        if(request.getDept() != null) {
            Optional<DepartmentEntity> retd = departmentRepository.findByName(request.getDept());
            if(!ret.isPresent()) {
                return new ResponseEntity<>(new ModifyCourseResponse("department doesn't exist", null, null), HttpStatus.BAD_REQUEST);
            }
            course.setDepartment(retd.get());
        }

        if(request.getName() != null) {
            course.setName(request.getName());
        }
        if(request.getCredit() != null) {
            course.setCredit(request.getCredit());
        }
        if(request.getSemester() != null) {
            course.setSemester(request.getSemester());
        }
        if(request.getIntro() != null) {
            course.setIntro(request.getIntro());
        }
        courseRepository.save(course);

        return new ResponseEntity<>(new ModifyCourseResponse("ok", "", ""), HttpStatus.OK);
    }

    @GetMapping(path = "/info")
    @Authorization
    public ResponseEntity<GetCourseResponse> getInfo(String cid) {
        if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new GetCourseResponse("course non-exist", "", "", 0.0f,
                    null, ""), HttpStatus.BAD_REQUEST);
        }

        CourseEntity course = courseRepository.findById(cid).get();
        return new ResponseEntity<>(new GetCourseResponse("ok", course.getCid(), course.getName(),
                course.getCredit(), course.getSemester(), course.getIntro()), HttpStatus.OK);
    }

    //TODO: getClasses


    /*@PutMapping(path = "/student")
    @Authorization
    public ResponseEntity<AddStudentsResponse> addStudents(@CurrentUser UserEntity user,
                                                           @RequestBody AddStudentsRequest request) {
        String cid = request.getCid();
        String tid = request.getTid();
        Set<String> sids = request.getSids();

        if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new AddStudentsResponse("permission denied", sids), HttpStatus.FORBIDDEN);
        } else if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new AddStudentsResponse("course doesn't exist", sids), HttpStatus.BAD_REQUEST);
        }

        CourseEntity course = courseRepository.findById(cid).get();
        TeachesEntity teaches = null;
        for (SectionEntity section : course.getSections()) {
            if (section.getTeaches().getTeacher().getUid() == tid) {
                teaches = section.getTeaches();
            }
        }
        if (teaches == null) {
            return new ResponseEntity<>(new AddStudentsResponse("No such instructor in this course", sids), HttpStatus.BAD_REQUEST);
        }
        //TODO: find by tid and cid directly

        Set<String> fail = new HashSet<>();
        for (String uid : sids) {
            if (!userRepository.existsById(uid)) {
                fail.add(uid);
            }
        }
        if (!fail.isEmpty()) {
            return new ResponseEntity<>(new AddStudentsResponse("uids don't exist", fail), HttpStatus.BAD_REQUEST);
        }

        for (String uid : sids) {
            if (userRepository.findById(uid).get().getType() != UserEntity.TYPE_STUDENT) {
                fail.add(uid);
            }
        }
        if (!fail.isEmpty()) {
            return new ResponseEntity<>(new AddStudentsResponse("users are not students", fail), HttpStatus.BAD_REQUEST);
        }

        for (String uid : sids) {
            TakesEntity takes = new TakesEntity();
            takes.setStudent(userRepository.findById(uid).get());
            takes.setTeaches(teaches);
            takes.setCid(teaches.getCid());
            //takes.setYear(curYear); TODO: keep current school year and semester
            //takes.setSemester(curSemester)
            takesRepository.save(takes);
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
        if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new DeleteStudentsResponse("permission denied"), HttpStatus.FORBIDDEN);
        } else if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new DeleteStudentsResponse("course doesn't exist"), HttpStatus.BAD_REQUEST);
        }

        CourseEntity course = courseRepository.findById(cid).get();
        TeachesEntity teaches = null;
        for (SectionEntity section : course.getSections()) {
            if (section.getTeaches().getTeacher().getUid() == tid) {
                teaches = section.getTeaches();
            }
        }
        if (teaches == null) {
            return new ResponseEntity<>(new DeleteStudentsResponse("No such instructor in this course"), HttpStatus.BAD_REQUEST);
        }

        for (TakesEntity takes : teaches.getTakes()) {
            if (uids.contains(takes.getStudent().getUid())) {
                takesRepository.delete(takes);
            }
        }
        return new ResponseEntity<>(new DeleteStudentsResponse("OK"), HttpStatus.OK);
    }*/

    /*@GetMapping(path = "/student")
    @Authorization
    public ResponseEntity<GetStudentsResponse> getStudents(@RequestBody GetStudentsRequest request) {
        String cid = request.getCid();
        if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new GetStudentsResponse("course non-exist", null, null, null), HttpStatus.BAD_REQUEST);
        }

        CourseEntity course = courseRepository.findById(cid).get();
        TeachesEntity teaches = null;
        for (SectionEntity section : course.getSections()) {
            if (section.getTeaches().getTeacher().getUid() == request.getUid()) {
                teaches = section.getTeaches();
            }
        }
        if (teaches == null) {
            return new ResponseEntity<>(new GetStudentsResponse("No such instructor in this course", null, null, null), HttpStatus.BAD_REQUEST);
        }
        //TODO: find by tid and cid and year and semester directly
        Set<String> record = new HashSet<>();
        List<String> uids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        UserEntity student;
        for(TakesEntity takes : teaches.getTakes()) {
            student = takes.getStudent();
            uids.add(student.getUid());
            names.add(student.getName());
            if(request.getNeedGrades()) {
                scores.add(takes.getScore());
            }
        }
        return new ResponseEntity<>(new GetStudentsResponse("ok", uids, names, scores), HttpStatus.OK);
    }


    @GetMapping(path = "/takes")
    @Authorization
    public ResponseEntity<GetStuCoursesResponse> getStuCourses(@CurrentUser UserEntity user,
                                                               @RequestBody GetStuCoursesRequest request) {
        String uid = request.getUid();
        if(!userRepository.existsById(uid)) {
            return new ResponseEntity<>(new GetStuCoursesResponse("uids don't exist", null, null, null, null), HttpStatus.BAD_REQUEST);
        }
        else if(uid != user.getUid() && user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new GetStuCoursesResponse("permission denied", null, null, null, null), HttpStatus.FORBIDDEN);
        }
        user = userRepository.findById(uid).get();
        List<String> cids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Float> credits = new ArrayList<>();
        List<Integer> grades = new ArrayList<>();
        CourseEntity course;
        for(TakesEntity takes : user.getTakes()) {
            course = courseRepository.findById(takes.getCid()).get();
            cids.add(course.getCid());
            names.add(course.getName());
            credits.add(course.getCredit());
            if(request.getNeedGrades()) {
                grades.add(takes.getScore());
            }

        }
        return new ResponseEntity<>(new GetStuCoursesResponse("ok", cids, names, credits, grades), HttpStatus.OK);
    }*/



}
