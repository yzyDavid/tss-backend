package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.ClassroomEntity;
import tss.entities.UserEntity;
import tss.repositories.ClassRepository;
import tss.repositories.ClassroomRepository;
import tss.repositories.TeachesRepository;
import tss.repositories.UserRepository;
import tss.requests.information.AddClassroomRequest;
import tss.requests.information.ModifyClassroomRequest;
import tss.responses.information.ClassroomResponse;

import java.util.Optional;

/**
 * @author cbq
 * @author reeve
 */
@Controller
@RequestMapping(path = "/classroom-management")
public class ClassroomController {

    private final ClassRepository classRepository;
    private final ClassroomRepository classroomRepository;
    private final TeachesRepository teachesRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClassroomController(ClassRepository classRepository, ClassroomRepository classroomRepository, TeachesRepository teachesRepository, UserRepository userRepository) {
        this.classRepository = classRepository;
        this.classroomRepository = classroomRepository;
        this.teachesRepository = teachesRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/classrooms")
//    @Authorization
    public ResponseEntity<ClassroomResponse> addClassroom(// @CurrentUser UserEntity user,
                                                          @RequestBody AddClassroomRequest request) {
//        if (user.getType() != UserEntity.TYPE_MANAGER) {
//            return new ResponseEntity<>(
//                    new ClassroomResponse("permission denied", null, null, null),
//                    HttpStatus.FORBIDDEN);
//        }

        ClassroomEntity classroom = new ClassroomEntity();
        classroom.setBuilding(request.getBuilding());
        classroom.setCapactity(request.getCapacity());
        classroom.setRoom(request.getRoom());
        // Use the returned instance of the save operation.
        classroom = classroomRepository.save(classroom);

        return new ResponseEntity<>(
                new ClassroomResponse("OK", classroom.getId(), classroom.getRoom(), classroom.getCapactity()),
                HttpStatus.OK);
    }

    @PutMapping(path = "/classrooms/{classroomId}")
//    @Authorization
    public ResponseEntity<ClassroomResponse> addClassroom(// @CurrentUser UserEntity user,
                                                          @PathVariable int classroomId,
                                                          @RequestBody AddClassroomRequest request) {
//        if (user.getType() != UserEntity.TYPE_MANAGER) {
//            return new ResponseEntity<>(
//                    new ClassroomResponse("permission denied", null, null, null),
//                    HttpStatus.FORBIDDEN);
//        }

        ClassroomEntity classroom = new ClassroomEntity();
        classroom.setId(classroomId);
        classroom.setBuilding(request.getBuilding());
        classroom.setCapactity(request.getCapacity());
        classroom.setRoom(request.getRoom());
        classroom = classroomRepository.save(classroom);

        return new ResponseEntity<>(
                new ClassroomResponse("OK", classroom.getId(), classroom.getRoom(), classroom.getCapactity()),
                HttpStatus.OK);
    }

    @GetMapping(path = "classrooms/{classroomId}")
//    @Authorization
    public ResponseEntity<ClassroomResponse> getClassroom(@PathVariable int classroomId) {
        Optional<ClassroomEntity> ret = classroomRepository.findById(classroomId);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(
                    new ClassroomResponse("classroomId doesn't exist", null, null, null),
                    HttpStatus.BAD_REQUEST);
        }

        ClassroomEntity classroom = ret.get();
        return new ResponseEntity<>(
                new ClassroomResponse("OK", classroom.getId(), classroom.getRoom(), classroom.getCapactity()),
                HttpStatus.OK);

    }

    @DeleteMapping(path = "/classrooms/{classroomId}")
//    @Authorization
    public ResponseEntity<ClassroomResponse> deleteClassroom(// @CurrentUser UserEntity user,
                                                             @PathVariable int classroomId) {
//        if (user.getType() != UserEntity.TYPE_MANAGER) {
//            return new ResponseEntity<>(
//                    new ClassroomResponse("permission denied", null, null, null),
//                    HttpStatus.FORBIDDEN);
//        }

        Optional<ClassroomEntity> ret = classroomRepository.findById(classroomId);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(
                    new ClassroomResponse("classroomId doesn't exist", null, null, null),
                    HttpStatus.BAD_REQUEST);
        }

        ClassroomEntity classroom = ret.get();
        classroomRepository.deleteById(classroomId);

        return new ResponseEntity<>(
                new ClassroomResponse("OK", classroom.getId(), classroom.getRoom(), classroom.getCapactity()),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/classrooms/{classroomId}")
//    @Authorization
    public ResponseEntity<ClassroomResponse> modifyClassroom(// @CurrentUser UserEntity user,
                                                             @PathVariable int classroomId,
                                                             @RequestBody ModifyClassroomRequest request) {
//        if (user.getType() != UserEntity.TYPE_MANAGER) {
//            return new ResponseEntity<>(
//                    new ClassroomResponse("permission denied", null, null, null),
//                    HttpStatus.FORBIDDEN);
//        }

        Optional<ClassroomEntity> ret = classroomRepository.findById(classroomId);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(
                    new ClassroomResponse("classroomId doesn't exist", null, null, null),
                    HttpStatus.BAD_REQUEST);
        }

        ClassroomEntity classroom = ret.get();
        if (request.getBuilding() != null) {
            classroom.setBuilding(request.getBuilding());
        }
        if (request.getCapacity() != null) {
            classroom.setCapactity(request.getCapacity());
        }
        if (request.getRoom() != null) {
            classroom.setRoom(request.getRoom());
        }
        classroom = classroomRepository.save(classroom);

        return new ResponseEntity<>(
                new ClassroomResponse("OK", classroom.getId(), classroom.getRoom(), classroom.getCapactity()),
                HttpStatus.OK);
    }

//    @GetMapping(path = "/classrooms/{classroomId}/schedule")
//    @Authorization
//    public ResponseEntity<ClassroomScheduleResponse> getSchedule(@CurrentUser UserEntity user,
//                                                                 @PathVariable int classroomId) {
//
//        if (user.getType() != UserEntity.TYPE_MANAGER) {
//            return new ResponseEntity<>(new ClassroomScheduleResponse("permission denied", ), HttpStatus.FORBIDDEN);
//        }
//
//        String building;
//        Integer room;
//        List<String> courseName = new ArrayList<String>();
//        List<String> day = new ArrayList<String>();
//        List<Integer> start = new ArrayList<Integer>();
//        List<Integer> end = new ArrayList<Integer>();
//        List<String> teacherName = new ArrayList<String>();
//
//        Optional<ClassroomEntity> ret = classroomRepository.findById(request.getCrid());
//        if (!ret.isPresent()) {
//            return new ResponseEntity<>(new ClassroomScheduleResponse("No classroom find!", null, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
//        }
//
//        ClassroomEntity classroom = ret.get();
//        building = classroom.getBuilding();
//        room = classroom.getRoom();
//
//        Set<SectionEntity> sectionEntities = classroom.getSections();
//        for (SectionEntity sectionEntity : sectionEntities) {
//            TimeSlotEntity time = sectionEntity.getTimeSlot();
//            day.add(time.getDay());
//            start.add(time.getStart());
//            end.add(time.getEnd());
//
//            ClassEntity classEntity = sectionEntity.get_class();
//            courseName.add(classEntity.getCourse().getName());
//
//            StringBuffer teachers = new StringBuffer();
//            Set<TeachesEntity> teachesEntities = classEntity.getTeaches();
//            for (TeachesEntity teachesEntity : teachesEntities) {
//                teachers.append("/");
//                teachers.append(teachesEntity.getTeacher().getName());
//            }
//            teacherName.add(teachers.toString());
//        }
//
//        return new ResponseEntity<>(new ClassroomScheduleResponse("OK", building, room, courseName, day, start, end, teacherName), HttpStatus.OK);
//    }

//    @PostMapping(path = "/query")
//    @Authorization
//    public ResponseEntity<QueryScheduleResponse> querySchedule(@CurrentUser UserEntity user,
//                                                               @RequestBody QueryScheduleRequest request) {
//
//        if(user.getType() != UserEntity.TYPE_MANAGER){
//            return new ResponseEntity<>(new QueryScheduleResponse("permission denied."), HttpStatus.FORBIDDEN);
//        }
//
//        List<String> courseName = new ArrayList<String>();
//        List<String> day = new ArrayList<String>();
//        List<Integer> start = new ArrayList<Integer>();
//        List<Integer> end = new ArrayList<Integer>();
//        List<String> building = new ArrayList<String>();
//        List<Integer> room = new ArrayList<Integer>();
//
//        Optional<UserEntity> retu = userRepository.findById(request.getUid());
//        if (!retu.isPresent()) {
//            return new ResponseEntity<>(new QueryScheduleResponse("User doesn't exist.", null, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
//        }
//        UserEntity teacher = retu.get();
//        if (teacher.getType() != UserEntity.TYPE_TEACHER) {
//            return new ResponseEntity<>(new QueryScheduleResponse("User is not a teacher!", null, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
//        }
//        String teacherName = teacher.getName();
//        Set<TeachesEntity> teachesEntities = teacher.getTeaches();
//
//        for (TeachesEntity teach : teachesEntities) {
//            CourseEntity course = teach.getCourse();
//            courseName.add(course.getName());
//
//            Set<ClassEntity> classEntities = teach.getClasses();
//            for (ClassEntity classEntity : classEntities) {
//
//                Set<SectionEntity> sectionEntities = classEntity.getSections();
//                for (SectionEntity sectionEntity : sectionEntities) {
//
//                    TimeSlotEntity time = sectionEntity.getTimeSlot();
//                    day.add(time.getDay());
//                    start.add(time.getStart());
//                    end.add(time.getEnd());
//
//                    ClassroomEntity classroom = sectionEntity.getClassroom();
//                    building.add(classroom.getBuilding());
//                    room.add(classroom.getRoom());
//                }
//
//            }
//
//        }
//
//        return new ResponseEntity<>(new QueryScheduleResponse("OK", teacherName, courseName, day, start, end, building, room), HttpStatus.OK);
//    }
}
