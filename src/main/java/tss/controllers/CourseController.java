package tss.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import tss.annotations.session.Authorization;
import tss.entities.CourseEntity;
import tss.entities.DepartmentEntity;
import tss.entities.SemesterEnum;
import tss.repositories.CourseRepository;
import tss.repositories.DepartmentRepository;
import tss.requests.information.*;
import tss.responses.information.*;
import tss.services.QueryService;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(path = "/course")
public class CourseController {
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final QueryService queryService;


    @Autowired
    CourseController(CourseRepository courseRepository, DepartmentRepository departmentRepository,
                     QueryService queryService) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
        this.queryService = queryService;
    }

    @PutMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddCourseResponse> addCourse(@RequestBody AddCourseRequest request) {
        String cid = request.getCid();
        if (courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new AddCourseResponse("failed with duplicated cid", cid, null,
                    null, null, null), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity dept = null;

        if (request.getDepartment() != null) {
            Optional<DepartmentEntity> ret = departmentRepository.findByName(request.getDepartment());
            if (!ret.isPresent()) {
                return new ResponseEntity<>(new AddCourseResponse("Department doesn't exist", null, null,
                        null, null, request.getDepartment()), HttpStatus.BAD_REQUEST);

            }
            dept = ret.get();
        }
        CourseEntity course = new CourseEntity();
        course.setId(request.getCid());
        course.setName(request.getName());
        course.setCredit(request.getCredit());
        course.setNumLessonsEachWeek(request.getNumLessonsEachWeek());
        if(dept != null) {
            course.setDepartment(dept);
        }
        courseRepository.save(course);

        return new ResponseEntity<>(new AddCourseResponse("ok", course.getId(), course.getName(), course.getCredit(),
                course.getNumLessonsEachWeek(), course.readDepartmentName()), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteCourseResponse> deleteCourse(@RequestBody DeleteCourseRequest request) {
        String cid = request.getCid();
        if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new DeleteCourseResponse("Non-exist course", cid, null), HttpStatus.BAD_REQUEST);
        }
        CourseEntity course = courseRepository.findById(cid).get();
        String name = course.getName();
        courseRepository.delete(course);

        return new ResponseEntity<>(new DeleteCourseResponse("ok", cid, name), HttpStatus.OK);

    }

    @PostMapping(path = "/modify")
    @Authorization
    public ResponseEntity<ModifyCourseResponse> modifyInfo(@RequestBody ModifyCourseRequest request) {
        String cid = request.getCid();
        Optional<CourseEntity> ret = courseRepository.findById(cid);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyCourseResponse("course doesn't exist", cid, null,
                    null, null, null, null), HttpStatus.BAD_REQUEST);
        }
        CourseEntity course = ret.get();
        DepartmentEntity dept = null;

        if (request.getDepartment() != null) {
            Optional<DepartmentEntity> retd = departmentRepository.findByName(request.getDepartment());
            if (!retd.isPresent()) {
                return new ResponseEntity<>(new ModifyCourseResponse("department doesn't exist", null, null,
                        null, null, request.getDepartment(), null), HttpStatus.BAD_REQUEST);

            }
            course.setDepartment(retd.get());
        }

        if(request.getName() != null) {
            course.setName(request.getName());
        }
        if(request.getCredit() != null) {
            course.setCredit(request.getCredit());
        }

        if(request.getNumLessonsEachWeek() != null) {
            course.setNumLessonsEachWeek(request.getNumLessonsEachWeek());

        }
        if(request.getIntro() != null) {
            course.setIntro(request.getIntro());
        }
        courseRepository.save(course);

        return new ResponseEntity<>(new ModifyCourseResponse("ok", course.getId(), course.getName(), course.getCredit(),
                course.getNumLessonsEachWeek(), course.readDepartmentName(), course.getIntro()), HttpStatus.OK);
    }

    @PostMapping(path = "/get/info")
    @Authorization
    public ResponseEntity<GetCourseResponse> getInfo(@RequestBody GetCourseRequest request) {
        Optional<CourseEntity> ret = courseRepository.findById(request.getCid());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetCourseResponse("course non-exist", request.getCid(), null,
                    null, null, null, null), HttpStatus.BAD_REQUEST);
        }
        CourseEntity course = ret.get();
        return new ResponseEntity<>(new GetCourseResponse("ok", course.getId(), course.getName(), course.getCredit(),
                course.getNumLessonsEachWeek(), course.readDepartmentName(), course.getIntro()), HttpStatus.OK);
    }

    @PostMapping(path = "/query")
    @Authorization
    public ResponseEntity<QueryCoursesResponse> queryCourses(@RequestBody QueryCoursesRequest request) {
        List<String> cids = new ArrayList<>();

        List<String> names = new ArrayList<>();
        List<String> departments = new ArrayList<>();
        Short deptId = null;
        if(request.getDepartment() != null) {
            Optional<DepartmentEntity> dept = departmentRepository.findByName(request.getDepartment());
            if (!dept.isPresent()) {
                return new ResponseEntity<>(new QueryCoursesResponse("Non-exist department", null, null, null), HttpStatus.OK);
            }
            deptId = dept.get().getId();
        }

        List<CourseEntity> ret = queryService.queryCourses(request.getCid(), request.getDepartment(), deptId);
        for (CourseEntity course : ret) {
            course = courseRepository.findById(course.getId()).get();
            cids.add(course.getId());
            names.add(course.getName());
            DepartmentEntity dept = course.getDepartment();
            String deptName = null;
            if (dept != null) {
                deptName = dept.getName();
            }
            departments.add(deptName);
        }
        return new ResponseEntity<>(new QueryCoursesResponse("OK", cids, names, departments), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<GetCoursesResponse> searchCourseByName(@RequestParam String name) {
        return new ResponseEntity<>(new GetCoursesResponse(courseRepository.findByNameLike("%"+name+"%")), HttpStatus.OK);
    }
}
