package tss.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.UserEntity;
import tss.requests.information.*;
import tss.responses.information.*;

/**
 * Created by apple on 2018/4/13.
 */
@Controller
@RequestMapping(path = "/classroom")
public class ClassroomController {

    public ClassroomController(){

    }

    @PutMapping(path = "/add")
    @Authorization public ResponseEntity<AddClassroomResponse> addClassroom(@CurrentUser UserEntity user, @RequestBody AddClassroomRequest request){


        return new ResponseEntity<>(new AddClassroomResponse("OK"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
    @Authorization public ResponseEntity<DeleteClassroomResponse> deleteClassroom(@CurrentUser UserEntity user, @RequestBody DeleteClassroomRequest request){


        return new ResponseEntity<>(new DeleteClassroomResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = "/info")
    @Authorization public ResponseEntity<ModifyClassroomResponse> modifyClassroom(@CurrentUser UserEntity user, @RequestBody ModifyClassroomRequest request){


        return new ResponseEntity<>(new ModifyClassroomResponse("OK"), HttpStatus.OK);
    }

    @GetMapping(path = "/print")
    @Authorization
    public ResponseEntity<PrintClassroomResponse> printClassroom(@CurrentUser UserEntity user, @RequestBody PrintClassroomRequest request){


        return new ResponseEntity<>(new PrintClassroomResponse("OK"), HttpStatus.OK);
    }


    @GetMapping(path = "/query")
    @Authorization
    public ResponseEntity<QueryScheduleResponse> querySchedule(@CurrentUser UserEntity user, @RequestBody QueryScheduleRequest request){


        return new ResponseEntity<>(new QueryScheduleResponse("OK"), HttpStatus.OK);
    }




}
