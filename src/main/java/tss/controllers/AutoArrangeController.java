package tss.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.UserEntity;
import tss.requests.information.AddClassRequest;
import tss.requests.information.AdjustScheduleRequest;
import tss.requests.information.AutoArrangeRequest;
import tss.requests.information.QueryScheduleRequest;
import tss.responses.information.AddClassResponse;
import tss.responses.information.AdjustScheduleResponse;
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

    @PutMapping(path = "/adjust")
    @Authorization
    public ResponseEntity<AdjustScheduleResponse> adjustClass(@CurrentUser UserEntity user,@RequestBody AdjustScheduleRequest request){


        return new ResponseEntity<>(new AdjustScheduleResponse("OK"), HttpStatus.OK);
    }

    @GetMapping(path = "/query")
    @Authorization
    public ResponseEntity<QueryScheduleResponse> printClass(@CurrentUser UserEntity user, @RequestBody QueryScheduleRequest request){


        return new ResponseEntity<>(new QueryScheduleResponse("OK"), HttpStatus.OK);
    }



}
