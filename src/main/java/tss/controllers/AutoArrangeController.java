package tss.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.UserEntity;
import tss.requests.information.ModifyScheduleRequest;
import tss.requests.information.AutoArrangeRequest;
import tss.requests.information.QueryScheduleRequest;
import tss.responses.information.ModifyScheduleResponse;
import tss.responses.information.AutoArrangeResponse;
import tss.responses.information.QueryScheduleResponse;


/**
 * Created by cbq on 2018/4/13.
 */
@Controller
@RequestMapping(path = "/arrange")
public class AutoArrangeController {

    public AutoArrangeController(){

    }

    @PutMapping(path = "/auto")
    @Authorization
    public ResponseEntity<AutoArrangeResponse> autoArrange(@CurrentUser UserEntity user, @RequestBody AutoArrangeRequest request){


        return new ResponseEntity<>(new AutoArrangeResponse("OK"), HttpStatus.OK);
    }



    @PostMapping(path = "/modify")
    @Authorization
    public ResponseEntity<ModifyScheduleResponse> modifySchedule(@CurrentUser UserEntity user, @RequestBody ModifyScheduleRequest request){


        return new ResponseEntity<>(new ModifyScheduleResponse("OK"), HttpStatus.OK);
    }

}
