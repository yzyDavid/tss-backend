package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.entities.ClassroomEntity;
import tss.exceptions.ClassroomNotFoundException;
import tss.models.Classroom;
import tss.repositories.ClassroomRepository;

import java.util.Optional;

/**
 * @author cbq
 * @author reeve
 */
@RestController
@RequestMapping("/classrooms")
public class ClassroomController {
    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomController(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @GetMapping("/{classroomId}")
    @ResponseStatus(HttpStatus.OK)
    public Classroom getClassroom(@PathVariable int classroomId) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId).orElseThrow
                (ClassroomNotFoundException::new);
        return new Classroom(classroomEntity);
    }

    @DeleteMapping("/{classroomId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeClassroom(@PathVariable int classroomId) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId).orElseThrow
                (ClassroomNotFoundException::new);
        classroomRepository.delete(classroomEntity);
    }

    @PatchMapping("/{classroomId}")
    @ResponseStatus(HttpStatus.OK)
    public Classroom updateClassroom(@PathVariable int classroomId, @RequestBody Classroom classroom) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId).orElseThrow
                (ClassroomNotFoundException::new);
        if (classroom.getName() != null) {
            classroomEntity.setName(classroom.getName());
        }
        if (classroom.getCapacity() != null) {
            classroomEntity.setCapacity(classroom.getCapacity());
        }
        return new Classroom(classroomRepository.save(classroomEntity));
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
