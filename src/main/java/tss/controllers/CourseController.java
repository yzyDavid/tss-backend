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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mingqi Yi
 */
@Controller
@RequestMapping(path = "/course")
public class CourseController {
    private final CourseRepository courseRepository;
    private final TeachesRepository teachesRepository;
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ClassroomRepository classroomRepository;
    private final TakesRepository takesRepository;
    private final ClassRepository classRepository;


    @Autowired
    CourseController(CourseRepository courseRepository, TeachesRepository teachesRepository,
                     UserRepository userRepository, TimeSlotRepository timeSlotRepository,
                     ClassroomRepository classroomRepository, TakesRepository takesRepository,
                    ClassRepository classRepository) {
        this.courseRepository = courseRepository;
        this.teachesRepository = teachesRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.takesRepository = takesRepository;
        this.classRepository = classRepository;
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
        if(request.getName() != null) {
            course.setName(request.getName());
        }
        if(request.getCredit() != null) {
            course.setCredit(request.getCredit());
        }
        if(request.getSemester() != null) {
            course.setSemester(request.getSemester());
        }
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
            return new ResponseEntity<>(new GetInstructorsResponse("course non-exist", null, null, null, null, null), HttpStatus.BAD_REQUEST);
        }

        CourseEntity course = courseRepository.findById(cid).get();
        List<Long> ids = new ArrayList<>();
        List<String> tids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<List<String>> times = new ArrayList<>();
        List<List<String>> classrooms = new ArrayList<>();
        for (ClassEntity c : course.getClasses()) {
            TeachesEntity teaches = c.getTeaches();
            UserEntity teachers = teaches.getTeacher();
            ids.add(teaches.getId());
            names.add(teachers.getName());
            tids.add(teachers.getUid());
            List<String> time = new ArrayList<>();
            List<String> location = new ArrayList<>();
            for(SectionEntity section : c.getSections()) {
                TimeSlotEntity timeSlot = section.getTimeSlot();
                ClassroomEntity classroom = section.getClassroom();
                time.add(timeSlot.getDay() + " " + timeSlot.getStart() + "-" + timeSlot.getEnd());
                times.add(time);
                location.add(classroom.getBuilding() + " " + classroom.getRoom());
                classrooms.add(location);
            }

        }
        return new ResponseEntity<>(new GetInstructorsResponse("ok", ids, tids, names, times, classrooms), HttpStatus.OK);

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

        CourseEntity course = courseRepository.findById(cid).get();
        for (String uid : uids) {
            TeachesEntity teaches = new TeachesEntity(userRepository.findById(uid).get(), course);
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
        for(TeachesEntity teaches: course.getTeaches()) {
            if(uids.contains(teaches.getTeacher().getUid()))
                teachesRepository.delete(teaches);
        }
        return new ResponseEntity<>(new DeleteInstructorsResponse("OK"), HttpStatus.OK);
    }

    @PutMapping(path = "/class")
    @Authorization
    public ResponseEntity<AddClassResponse> addClass(@CurrentUser UserEntity user,
                                                     @RequestBody AddClassRequest request) {
        String cid = request.getCid();
        long tid = request.getTid();

        if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new AddClassResponse("permission denied"), HttpStatus.FORBIDDEN);
        } else if (!courseRepository.existsById(cid)) {
            return new ResponseEntity<>(new AddClassResponse("course doesn't exist"), HttpStatus.BAD_REQUEST);
        } else if(!teachesRepository.existsById(tid)) {
            return new ResponseEntity<>(new AddClassResponse("No such instructor in this course"), HttpStatus.BAD_REQUEST);
        }

        CourseEntity course = courseRepository.findById(cid).get();
        TeachesEntity teaches = teachesRepository.findById(tid).get();
        ClassEntity newClass = new ClassEntity();
        newClass.setCapacity(request.getCapacity());
        newClass.setYear(request.getYear());
        newClass.setTeaches(teaches);
        newClass.setCourse(course);
        classRepository.save(newClass);
        return new ResponseEntity<>(new AddClassResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = "/class")
    @Authorization
    public ResponseEntity<ModifyClassResponse> modifyClass(@CurrentUser UserEntity user,
                                                     @RequestBody ModifyClassRequest request) {
        Long cid = request.getCid();

        if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new ModifyClassResponse("permission denied"), HttpStatus.FORBIDDEN);
        } else if(!classRepository.existsById(cid)) {
            return new ResponseEntity<>(new ModifyClassResponse("can't find the class"), HttpStatus.BAD_REQUEST);
        }


        ClassEntity c = classRepository.findById(cid).get();
        if(request.getUid() != null) {
            if(userRepository.existsById(request.getUid())) {
                UserEntity teacher = userRepository.findById(request.getUid()).get();
                if(teacher.getType() == UserEntity.TYPE_TEACHER) {
                    int flag = 0;
                    for(TeachesEntity teaches : teacher.getTeaches()) {
                        if(teaches.getCourse().getCid().equals(cid)) {
                            c.setTeaches(teaches);
                            flag = 1;
                        }
                    }
                    if(flag == 0)
                        return new ResponseEntity<>(new ModifyClassResponse("The teacher doesn't teach this class"), HttpStatus.BAD_REQUEST);
                }
                else {
                    return new ResponseEntity<>(new ModifyClassResponse("That user is not a teacher"), HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(new ModifyClassResponse("user doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        if(request.getCapacity() != null) {
            c.setCapacity(request.getCapacity());
        }
        if(request.getYear() != null) {
            c.setYear(request.getYear());
        }
        classRepository.save(c);
        return new ResponseEntity<>(new ModifyClassResponse("OK"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/class")
    @Authorization
    public ResponseEntity<DeleteClassesResponse> deleteClasses(@CurrentUser UserEntity user,
                                                           @RequestBody DeleteClassesRequest request) {
        List<Long> cids = request.getIds();
        List<Long> failIds = new ArrayList<>();
        for(Long cid : cids) {
            if(!classRepository.existsById(cid)) {
                failIds.add(cid);
            }
        }
        if(!failIds.isEmpty()) {
            return new ResponseEntity<>(new DeleteClassesResponse("Some Class don't exist", failIds), HttpStatus.BAD_REQUEST);
        }
        for(Long cid : cids) {
            classRepository.deleteById(cid);
        }
        return new ResponseEntity<>(new DeleteClassesResponse("OK", failIds), HttpStatus.OK);
    }

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
