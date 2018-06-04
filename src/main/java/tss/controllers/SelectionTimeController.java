package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.SelectionTimeEntity;
import tss.entities.UserEntity;
import tss.exceptions.PermissionDeniedException;
import tss.models.Clazz;
import tss.repositories.*;
import tss.requests.information.AddSelectionTimeRequest;
import tss.responses.information.BasicResponse;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * @author NeverMore2744 - ljh
 */
@RestController
@RequestMapping()
public class SelectionTimeController {
    private final SelectionTimeRepository selectionTimeRepository;

    @Autowired
    public SelectionTimeController(SelectionTimeRepository selectionTimeRepository) {
        this.selectionTimeRepository = selectionTimeRepository;
    }

    @PostMapping("/selection_time/register")
    @Authorization
    @ResponseStatus(HttpStatus.CREATED)
    public BasicResponse addRegisterTime(@CurrentUser UserEntity user, @RequestBody AddSelectionTimeRequest request) {
        Timestamp startTime = request.getStartTime();
        Timestamp endTime = request.getEndTime();


        if (!user.readTypeName().equals("System administrator") && !user.readTypeName().equals("Teaching administrator")) {
            throw new PermissionDeniedException();
        }

        SelectionTimeEntity selectionTimeEntity;
        Optional<SelectionTimeEntity> selectionTimeEntityOptional = selectionTimeRepository.findByStartAndEnd(startTime, endTime);
        if (!selectionTimeEntityOptional.isPresent()) {
            selectionTimeEntity = new SelectionTimeEntity();
            selectionTimeEntity.setComplement(false);
            selectionTimeEntity.setDrop(false);
            selectionTimeEntity.setRegister(true);
            selectionTimeEntity.setStart(startTime);
            selectionTimeEntity.setEnd(endTime);
        }
        else {
            selectionTimeEntity = selectionTimeEntityOptional.get();
            selectionTimeEntity.setRegister(true);
        }
        selectionTimeRepository.save(selectionTimeEntity);

        return new BasicResponse("Set register time successfully.");
    }

    @PostMapping("/selection_time/complement")
    @Authorization
    @ResponseStatus(HttpStatus.CREATED)
    public BasicResponse addComplementTime(@CurrentUser UserEntity user, @RequestBody AddSelectionTimeRequest request) {
        Timestamp startTime = request.getStartTime();
        Timestamp endTime = request.getEndTime();

        if (!user.readTypeName().equals("System administrator") && !user.readTypeName().equals("Teaching administrator")) {
            throw new PermissionDeniedException();
        }

        SelectionTimeEntity selectionTimeEntity;
        Optional<SelectionTimeEntity> selectionTimeEntityOptional = selectionTimeRepository.findByStartAndEnd(startTime, endTime);
        if (!selectionTimeEntityOptional.isPresent()) {
            selectionTimeEntity = new SelectionTimeEntity();
            selectionTimeEntity.setComplement(true);
            selectionTimeEntity.setDrop(false);
            selectionTimeEntity.setRegister(false);
            selectionTimeEntity.setStart(startTime);
            selectionTimeEntity.setEnd(endTime);
        }
        else {
            selectionTimeEntity = selectionTimeEntityOptional.get();
            selectionTimeEntity.setComplement(true);
        }

        selectionTimeRepository.save(selectionTimeEntity);

        return new BasicResponse("Set complement time successfully.");
    }

    @PostMapping("/selection_time/drop")
    @Authorization
    @ResponseStatus(HttpStatus.CREATED)
    public BasicResponse addDropTime(@CurrentUser UserEntity user, @RequestBody AddSelectionTimeRequest request) {
        Timestamp startTime = request.getStartTime();
        Timestamp endTime = request.getEndTime();

        if (!user.readTypeName().equals("System administrator") && !user.readTypeName().equals("Teaching administrator")) {
            throw new PermissionDeniedException();
        }

        SelectionTimeEntity selectionTimeEntity;
        Optional<SelectionTimeEntity> selectionTimeEntityOptional = selectionTimeRepository.findByStartAndEnd(startTime, endTime);
        if (!selectionTimeEntityOptional.isPresent()) {
            selectionTimeEntity = new SelectionTimeEntity();
            selectionTimeEntity.setComplement(false);
            selectionTimeEntity.setDrop(true);
            selectionTimeEntity.setRegister(false);
            selectionTimeEntity.setStart(startTime);
            selectionTimeEntity.setEnd(endTime);
        }
        else {
            selectionTimeEntity = selectionTimeEntityOptional.get();
            selectionTimeEntity.setComplement(true);
        }

        selectionTimeRepository.save(selectionTimeEntity);

        return new BasicResponse("Set drop time successfully.");
    }
}
