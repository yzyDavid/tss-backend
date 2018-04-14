package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.ClassroomEntity;
import tss.entities.CourseEntity;
import tss.entities.TeachesEntity;
import tss.entities.UserEntity;
import tss.repositories.ClassRepository;
import tss.repositories.ClassroomRepository;
import tss.repositories.TeachesRepository;
import tss.requests.information.*;
import tss.responses.information.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by apple on 2018/4/13.
 */
@Controller
@RequestMapping(path = "/classroom")
public class ClassroomController {

    private final ClassRepository classRepository;
    private final ClassroomRepository classroomRepository;
    private final TeachesRepository teachesRepository;

    @Autowired
    public ClassroomController(ClassRepository classRepository, ClassroomRepository classroomRepository, TeachesRepository teachesRepository){
        this.classRepository = classRepository;
        this.classroomRepository = classroomRepository;
        this.teachesRepository = teachesRepository;
    }

    @PutMapping(path = "/add")
 //   @Authorization
    public ResponseEntity<AddClassroomResponse> addClassroom(//@CurrentUser UserEntity user,
                                                             @RequestBody AddClassroomRequest request){

        short cid = request.getCid();

//        if(user.getType() != UserEntity.TYPE_MANAGER){
//            return new ResponseEntity<>(new AddClassroomResponse("permission denied.", null, null), HttpStatus.FORBIDDEN);
//        }

        Optional<ClassroomEntity> ret = classroomRepository.findById(cid);
        if( ret.isPresent()){
            return new ResponseEntity<>(new AddClassroomResponse("classroom already exists.", null, null), HttpStatus.BAD_REQUEST);
        }

        ClassroomEntity classroom = new ClassroomEntity();
        classroom.setBuilding(request.getBuilding());
        classroom.setCapactity(request.getCapacity());
        classroom.setRoom(request.getRoom());
        classroom.setId(request.getCid());
        classroomRepository.save(classroom);


        return new ResponseEntity<>(new AddClassroomResponse("OK", Short.toString(cid), request.getRoom()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
//    @Authorization
    public ResponseEntity<DeleteClassroomResponse> deleteClassroom(//@CurrentUser UserEntity user,
                                                                   @RequestBody DeleteClassroomRequest request){

        short cid = request.getCid();

//        if(user.getType() != UserEntity.TYPE_MANAGER){
//            return new ResponseEntity<>(new DeleteClassroomResponse("permission denied."), HttpStatus.FORBIDDEN);
//        }

        Optional<ClassroomEntity> ret = classroomRepository.findById(cid);
        if( !ret.isPresent()){
            return new ResponseEntity<>(new DeleteClassroomResponse("classroom doesn't exist."), HttpStatus.BAD_REQUEST);
        }

        classroomRepository.deleteById(cid);
        return new ResponseEntity<>(new DeleteClassroomResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = "/info")
//    @Authorization
    public ResponseEntity<ModifyClassroomResponse> modifyClassroom(//@CurrentUser UserEntity user,
                                                                   @RequestBody ModifyClassroomRequest request){

        short cid = request.getCid();

//        if(user.getType() != UserEntity.TYPE_MANAGER){
//            return new ResponseEntity<>(new ModifyClassroomResponse("permission denied."), HttpStatus.FORBIDDEN);
//        }

        Optional<ClassroomEntity> ret = classroomRepository.findById(cid);
        if( !ret.isPresent()){
            return new ResponseEntity<>(new ModifyClassroomResponse("can't find the classroom."), HttpStatus.BAD_REQUEST);
        }

        ClassroomEntity classroom = ret.get();
        if(request.getBuilding() != null){
            classroom.setBuilding(request.getBuilding());
        }
        if(request.getCapacity() != null){
            classroom.setCapactity(request.getCapacity());
        }
        if(request.getRoom() != null) {
            classroom.setRoom(request.getRoom());
        }
        classroomRepository.save(classroom);

        return new ResponseEntity<>(new ModifyClassroomResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = "/print")
//    @Authorization
    public ResponseEntity<PrintClassroomResponse> printClassroom(//@CurrentUser UserEntity user,
                                                                 @RequestBody PrintClassroomRequest request){

//        if(user.getType() != UserEntity.TYPE_MANAGER){
//            return new ResponseEntity<>(new PrintClassroomResponse("permission denied."), HttpStatus.FORBIDDEN);
//        }


        return new ResponseEntity<>(new PrintClassroomResponse("OK"), HttpStatus.OK);
    }


    @PostMapping(path = "/query")
//    @Authorization
    public ResponseEntity<QueryScheduleResponse> querySchedule(//@CurrentUser UserEntity user,
                                                               @RequestBody QueryScheduleRequest request){

//        if(user.getType() != UserEntity.TYPE_MANAGER){
//            return new ResponseEntity<>(new QueryScheduleResponse("permission denied."), HttpStatus.FORBIDDEN);
//        }


        //for test only
        List<String> courseName = new ArrayList<String>();
        List<String> day = new ArrayList<String>();
        List<Integer> start = new ArrayList<Integer>();
        List<Integer> end = new ArrayList<Integer>();;
        List<String> building = new ArrayList<String>();
        List<Integer> room = new ArrayList<Integer>();;

        courseName.add("SE");
        day.add("Thursday");
        start.add(13);
        end.add(15);
        building.add("CaoGuangBiaoXi");
        room.add(201);

        return new ResponseEntity<>(new QueryScheduleResponse("OK",courseName , day, start, end, building, room), HttpStatus.OK);
    }




}
