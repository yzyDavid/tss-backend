package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.*;
import tss.repositories.CourseRepository;
import tss.repositories.SectionRepository;
import tss.repositories.TimeSlotRepository;
import tss.requests.information.ModifyScheduleRequest;
import tss.requests.information.AutoArrangeRequest;
import tss.responses.information.ModifyScheduleResponse;
import tss.responses.information.AutoArrangeResponse;

import java.util.Optional;


/**
 * Created by cbq on 2018/4/13.
 */
@Controller
@RequestMapping(path = "/arrange")
public class AutoArrangeController {

    private final SectionRepository sectionRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public AutoArrangeController(SectionRepository sectionRepository, TimeSlotRepository timeSlotRepository, CourseRepository courseRepository){
        this.sectionRepository = sectionRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.courseRepository = courseRepository;

    }

    @PutMapping(path = "/auto")
    @Authorization
    public ResponseEntity<AutoArrangeResponse> autoArrange(@CurrentUser UserEntity user,
                                                           @RequestBody AutoArrangeRequest request){

        if(user.getType() != UserEntity.TYPE_MANAGER){
            return new ResponseEntity<>(new AutoArrangeResponse("permission denied."), HttpStatus.FORBIDDEN);
        }

        for(CourseEntity course: courseRepository.findAll()){



        }





        return new ResponseEntity<>(new AutoArrangeResponse("Auto arrange finished."), HttpStatus.OK);
    }



    @PostMapping(path = "/modify")
    @Authorization
    public ResponseEntity<ModifyScheduleResponse> modifySchedule(@CurrentUser UserEntity user,
                                                                 @RequestBody ModifyScheduleRequest request){

        if(user.getType() != UserEntity.TYPE_MANAGER){
            return new ResponseEntity<>(new ModifyScheduleResponse("permission denied."), HttpStatus.FORBIDDEN);
        }

        Optional<SectionEntity> ret = sectionRepository.findById(request.getSid());
        if( !ret.isPresent()){
            return new ResponseEntity<>(new ModifyScheduleResponse("No such section, how could you change?"), HttpStatus.BAD_REQUEST);
        }

        SectionEntity oldSection = ret.get();
//        TimeSlotEntity timeSlot = oldSection.getTimeSlot();
//        ClassroomEntity classroom = oldSection.getClassroom();

        for(SectionEntity section: sectionRepository.findAll()){
            if(request.getNewBuilding().equals(section.getClassroom().getBuilding())
                    && request.getNewRoom().equals(section.getClassroom().getRoom())
                    && request.getNewDay().equals(section.getTimeSlot().getDay())) {

                if(request.getNewStart().equals(section.getTimeSlot().getStart()) && request.getNewEnd().equals(section.getTimeSlot().getEnd())){
                    return new ResponseEntity<>(new ModifyScheduleResponse("Schedule conficts."), HttpStatus.BAD_REQUEST);
                }


                if((request.getNewStart() - section.getTimeSlot().getStart())
                                * (request.getNewEnd() - section.getTimeSlot().getEnd()) < 0){
                    return new ResponseEntity<>(new ModifyScheduleResponse("Schedule conficts."), HttpStatus.BAD_REQUEST);
                }
            }
        }

        oldSection.getTimeSlot().setDay(request.getNewDay());
        oldSection.getTimeSlot().setStart(request.getNewStart());
        oldSection.getTimeSlot().setEnd(request.getNewEnd());
        oldSection.getClassroom().setRoom(request.getNewRoom());
        oldSection.getClassroom().setBuilding(request.getNewBuilding());

        sectionRepository.save(oldSection);

        return new ResponseEntity<>(new ModifyScheduleResponse("modify schedule succ."), HttpStatus.OK);
    }

}
