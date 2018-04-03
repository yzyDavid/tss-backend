package tss.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.information.*;
import tss.information.untapped.ClassroomEntity;
import tss.information.untapped.ClassroomRepository;
import tss.session.Authorization;
import tss.session.CurrentUser;

import java.util.*;

/**
 * @author Mingqi Yi
 */
@Controller
@RequestMapping(path = "/course")
public class CourseController {
    private CourseRepository courseRepository;
    private TeachesRepository teachesRepository;
    private UserRepository userRepository;
    private TimeSlotRepository timeSlotRepository;
    private ClassroomRepository classroomRepository;
    private TakesRepository takesRepository;


    @Autowired
    CourseController(CourseRepository courseRepository, TeachesRepository teachesRepository,
                     UserRepository userRepository, TimeSlotRepository timeSlotRepository,
                     ClassroomRepository classroomRepository, TakesRepository takesRepository) {
        this.courseRepository = courseRepository;
        this.teachesRepository = teachesRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.takesRepository = takesRepository;
    }

    @PutMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddCourseResponse> addCourse(@CurrentUser UserEntity user,
                                                       @RequestBody AddCourseRequest request) {
        String cid = request.getCid();
        if (courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new AddCourseResponse("failed with duplicated cid", "", ""), HttpStatus.BAD_REQUEST);
        } else if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new AddCourseResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
        }

        CourseEntity course = new CourseEntity();
        course.setCid(request.getCid());
        course.setName(request.getName());
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
        if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new DeleteCourseResponse("course non-exist", "", ""), HttpStatus.BAD_REQUEST);
        } else if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new DeleteCourseResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
        }

        CourseEntity course = courseRepository.findById(cid).get();
        courseRepository.delete(course);

        return new ResponseEntity<>(new DeleteCourseResponse("ok", course.getCid(), course.getName()), HttpStatus.OK);

    }


    @PostMapping(path = "/info")
    @Authorization
    public ResponseEntity<ModifyCourseResponse> modifyInfo(@CurrentUser UserEntity user,
                                                           @RequestBody ModifyCourseRequest request) {
        String cid = request.getCid();
        if (courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new ModifyCourseResponse("failed with duplicated cid", "", ""), HttpStatus.BAD_REQUEST);
        } else if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new ModifyCourseResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
        }
        CourseEntity course = courseRepository.findById(cid).get();
        course.setCid(request.getCid());
        course.setName(request.getName());
        course.setCredit(request.getCredit());
        course.setSemester(request.getSemester());
        course.setIntro(request.getIntro());
        courseRepository.save(course);

        return new ResponseEntity<>(new ModifyCourseResponse("ok", "", ""), HttpStatus.OK);

    }

    @GetMapping(path = "/info")
    public ResponseEntity<GetCourseResponse> getInfo(@RequestBody GetCourseRequest request) {
        String cid = request.getCid();
        if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new GetCourseResponse("course non-exist", "", "", 0.0f,
                    null, ""), HttpStatus.BAD_REQUEST);
        }

        CourseEntity course = courseRepository.findById(cid).get();
        return new ResponseEntity<>(new GetCourseResponse("ok", course.getCid(), course.getName(),
                course.getCredit(), course.getSemester(), course.getIntro()), HttpStatus.OK);

    }

    @GetMapping(path = "/instructor")
    public ResponseEntity<GetInstructorsResponse> getInstructors(@RequestBody GetInstructorsRequest request) {
        String cid = request.getCid();
        if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new GetInstructorsResponse("course non-exist", null, null, null, null), HttpStatus.BAD_REQUEST);
        }

        CourseEntity course = courseRepository.findById(cid).get();
        Set<String> record = new HashSet<>();
        List<String> tids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<List<String>> times = new ArrayList<>();
        List<List<String>> classrooms = new ArrayList<>();
        for (SectionEntity section : course.getSections()) {
            TeachesEntity teaches = section.getTeaches();
            UserEntity teacher = teaches.getTeacher();
            TimeSlotEntity timeSlot = section.getTimeSlot();
            ClassroomEntity classroom = section.getClassroom();
            String uid = teacher.getUid();
            if (!record.contains(uid)) {
                record.add(uid);
                tids.add(uid);
                names.add(teacher.getName());
                List<String> time = new ArrayList<>();
                time.add(timeSlot.getDay() + " " + timeSlot.getStart() + "-" + timeSlot.getEnd());
                times.add(time);
                List<String> location = new ArrayList<>();
                location.add(classroom.getBuilding() + " " + classroom.getRoom());
                classrooms.add(location);
            } else {
                int i = tids.indexOf(uid);
                List<String> time = times.get(i);
                time.add(timeSlot.getDay() + " " + timeSlot.getStart() + "-" + timeSlot.getEnd());
                List<String> location = classrooms.get(i);
                location.add(classroom.getBuilding() + " " + classroom.getRoom());

            }

        }
        return new ResponseEntity<>(new GetInstructorsResponse("ok", tids, names, times, classrooms), HttpStatus.OK);

    }

    @PutMapping(path = "/instructor")
    @Authorization
    public ResponseEntity<AddInstructorsResponse> addInstructors(@CurrentUser UserEntity user,
                                                                 @RequestBody AddInstructorsRequest request) {
        String cid = request.getCid();
        Set<String> uids = request.getUids();
        if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new AddInstructorsResponse("course doesn't exist", uids), HttpStatus.BAD_REQUEST);
        } else if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new AddInstructorsResponse("permission denied", uids), HttpStatus.FORBIDDEN);
        }

        Set<String> fail = new HashSet<>();
        for (String uid : uids) {
            if (!userRepository.existsById(uid)) {
                fail.add(uid);
            }
        }
        if (!fail.isEmpty()) {
            return new ResponseEntity<>(new AddInstructorsResponse("uids don't exist", fail), HttpStatus.BAD_REQUEST);
        }

        for (String uid : uids) {
            if (userRepository.findById(uid).get().getType() != UserEntity.TYPE_TEACHER) {
                fail.add(uid);
            }
        }
        if (!fail.isEmpty()) {
            return new ResponseEntity<>(new AddInstructorsResponse("users are not teachers", fail), HttpStatus.BAD_REQUEST);
        }

        for (String uid : uids) {
            TeachesEntity teaches = new TeachesEntity(userRepository.findById(uid).get(), cid);
            SectionEntity section = new SectionEntity(teaches, courseRepository.findById(cid).get());
            teachesRepository.save(teaches);
        }
        return new ResponseEntity<>(new AddInstructorsResponse("OK", null), HttpStatus.OK);
    }

    @DeleteMapping(path = "/instructor")
    @Authorization
    public ResponseEntity<DeleteInstructorsResponse> deleteInstructors(@CurrentUser UserEntity user,
                                                                       @RequestBody DeleteInstructorsRequest request) {
        String cid = request.getCid();
        Set<String> uids = request.getUids();
        if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new DeleteInstructorsResponse("course doesn't exist"), HttpStatus.BAD_REQUEST);
        } else if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new DeleteInstructorsResponse("permission denied"), HttpStatus.FORBIDDEN);
        }

        CourseEntity course = courseRepository.findById(cid).get();
        for (SectionEntity section : course.getSections()) {
            teachesRepository.delete(section.getTeaches());
        }
        return new ResponseEntity<>(new DeleteInstructorsResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = "/instructor")
    @Authorization
    public ResponseEntity<ModifyInstructorResponse> modifyInstructors(@CurrentUser UserEntity user,
                                                                      @RequestBody ModifyInstructorRequest request) {
        String cid = request.getCid();
        String uid = request.getUid();
        if (user.getType() != UserEntity.TYPE_MANAGER || user.getUid() != uid) {
            return new ResponseEntity<>(new ModifyInstructorResponse("permission denied"), HttpStatus.FORBIDDEN);
        } else if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new ModifyInstructorResponse("course doesn't exist"), HttpStatus.BAD_REQUEST);
        }

        CourseEntity course = courseRepository.findById(cid).get();
        SectionEntity section = null;
        TeachesEntity teaches = null;
        for (SectionEntity item : course.getSections()) {
            if (item.getTeaches().getTeacher().getUid() == uid) {
                section = item;
            }
        }
        if (section != null) {
            teaches = section.getTeaches();
        }
        if (teaches == null) {
            return new ResponseEntity<>(new ModifyInstructorResponse("Instructor doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        teaches.setCapacity(request.getCapacity());
        section.setSemester(request.getSemester());
        section.setClassroom(classroomRepository.findById(request.getClassroomId()).get());
        section.setTimeSlot(timeSlotRepository.findById(request.getTimeSlotId()).get());
        return new ResponseEntity<>(new ModifyInstructorResponse("OK"), HttpStatus.OK);
    }

    @PutMapping(path = "/student")
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
            //takes.setYear(curYear); TODO: keep school year
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
    }


}
