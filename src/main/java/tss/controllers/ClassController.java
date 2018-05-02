package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.*;
import tss.repositories.*;
import tss.requests.information.AddClassRequest;
import tss.requests.information.DeleteClassesRequest;
import tss.requests.information.GetInstructorsRequest;
import tss.requests.information.ModifyClassRequest;
import tss.responses.information.AddClassResponse;
import tss.responses.information.DeleteClassesResponse;
import tss.responses.information.GetInstructorsResponse;
import tss.responses.information.ModifyClassResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/class")
public class ClassController {
    private final CourseRepository courseRepository;
    private final TeachesRepository teachesRepository;
    private final UserRepository userRepository;
    private final TakesRepository takesRepository;
    private final ClassRepository classRepository;

    @Autowired
    public ClassController(CourseRepository courseRepository, TeachesRepository teachesRepository,
                           UserRepository userRepository, TakesRepository takesRepository,
                           ClassRepository classRepository) {
        this.courseRepository = courseRepository;
        this.teachesRepository = teachesRepository;
        this.userRepository = userRepository;
        this.takesRepository = takesRepository;
        this.classRepository = classRepository;
    }

    @PutMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddClassResponse> addClass(@CurrentUser UserEntity user,
                                                     @RequestBody AddClassRequest request) {
        String cid = request.getCid();

        Optional<CourseEntity> ret = courseRepository.findById(cid);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new AddClassResponse("course doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        CourseEntity course = ret.get();
        ClassEntity newClass = new ClassEntity();
        newClass.setCapacity(request.getCapacity());
        newClass.setYear(request.getYear());
        newClass.setCourse(course);
        classRepository.save(newClass);
        return new ResponseEntity<>(new AddClassResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = "/info")
    @Authorization
    public ResponseEntity<ModifyClassResponse> modifyClass(@CurrentUser UserEntity user,
                                                           @RequestBody ModifyClassRequest request) {
        Long cid = request.getCid();

        Optional<ClassEntity> ret = classRepository.findById(cid);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyClassResponse("can't find the class"), HttpStatus.BAD_REQUEST);
        }
        ClassEntity c = ret.get();
        if (request.getCapacity() != null) {
            c.setCapacity(request.getCapacity());
        }
        if (request.getYear() != null) {
            c.setYear(request.getYear());
        }
        classRepository.save(c);
        return new ResponseEntity<>(new ModifyClassResponse("OK"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteClassesResponse> deleteClasses(@CurrentUser UserEntity user,
                                                               @RequestBody DeleteClassesRequest request) {
        List<Long> cids = request.getIds();

        List<Long> failIds = new ArrayList<>();
        for (Long cid : cids) {
            if (!classRepository.existsById(cid)) {
                failIds.add(cid);
            }
        }
        if (!failIds.isEmpty()) {
            return new ResponseEntity<>(new DeleteClassesResponse("Some Class don't exist", failIds), HttpStatus.BAD_REQUEST);
        }
        for (Long cid : cids) {
            classRepository.deleteById(cid);
        }
        return new ResponseEntity<>(new DeleteClassesResponse("OK", failIds), HttpStatus.OK);
    }

    @PostMapping(path = "/getInstructors")
    @Authorization
    public ResponseEntity<GetInstructorsResponse> getInstructors(@RequestBody GetInstructorsRequest request) {

        Optional<ClassEntity> ret = classRepository.findById(request.getCid());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetInstructorsResponse("class non-exist", null, null, null, null), HttpStatus.BAD_REQUEST);
        }
        ClassEntity clazz = ret.get();
        List<String> tids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<List<String>> times = new ArrayList<>();
        List<List<String>> classrooms = new ArrayList<>();
        for (TeachesEntity teaches : clazz.getTeaches()) {
            UserEntity teacher = teaches.getTeacher();
            tids.add(teacher.getUid());
            names.add(teacher.getName());
            List<String> time = new ArrayList<>();
            List<String> location = new ArrayList<>();
            for (SectionEntity section : clazz.getSections()) {
                TimeSlotEntity timeSlot = section.getTimeSlot();
                ClassroomEntity classroom = section.getClassroom();
                time.add(timeSlot.getDay() + " " + timeSlot.getStart() + "-" + timeSlot.getEnd());
                times.add(time);
                location.add(classroom.getBuilding() + " " + classroom.getRoom());
                classrooms.add(location);
            }
        }
        return new ResponseEntity<>(new GetInstructorsResponse("ok", tids, names, times, classrooms), HttpStatus.OK);
    }
}