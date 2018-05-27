package tss.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tss.models.TimeSlotTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author reeve
 */
@RestController
@RequestMapping("/time-slot-types")
public class TimeSlotTypeController {
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Map<String, TimeSlotTypeEnum> getTimeSlotTypes() {
        TimeSlotTypeEnum[] allTimeSlotTypes = TimeSlotTypeEnum.values();
        Map<String, TimeSlotTypeEnum> timeSlotTypeMap = new HashMap<>(allTimeSlotTypes.length);

        for (TimeSlotTypeEnum timeSlotType : allTimeSlotTypes) {
            timeSlotTypeMap.put(timeSlotType.name(), timeSlotType);
        }
        return timeSlotTypeMap;
    }
}
