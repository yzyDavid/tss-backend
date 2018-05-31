package tss.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.entities.ClassroomEntity;
import tss.entities.CurrentYearSemesterOfArrangementEntity;
import tss.entities.TimeSlotEntity;
import tss.exceptions.CurrentYearSemesterOfArrangementNotFoundException;
import tss.models.YearSemester;
import tss.repositories.ClassRepository;
import tss.repositories.ClassroomRepository;
import tss.repositories.CurrentYearSemesterOfArrangementRepository;
import tss.repositories.TimeSlotRepository;

import java.util.List;

/**
 * @author reeve
 */
@RestController
@RequestMapping("/current-year-semester-of-arrangement")
public class CurrentYearSemesterOfArrangementController {

    private CurrentYearSemesterOfArrangementRepository currentYearSemesterOfArrangementRepository;
    private ClassroomRepository classroomRepository;
    private TimeSlotRepository timeSlotRepository;

    public CurrentYearSemesterOfArrangementController(
            CurrentYearSemesterOfArrangementRepository currentYearSemesterOfArrangementRepository,
            ClassroomRepository classroomRepository, TimeSlotRepository timeSlotRepository) {
        this.currentYearSemesterOfArrangementRepository = currentYearSemesterOfArrangementRepository;
        this.classroomRepository = classroomRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setCurrentYearSemesterOfArrangement(@RequestBody YearSemester yearSemester) {

        // Invalidate the old one.
        List<CurrentYearSemesterOfArrangementEntity> validYearSemesters = currentYearSemesterOfArrangementRepository
                .findByValid(true);
        for (CurrentYearSemesterOfArrangementEntity validYearSemester : validYearSemesters) {
            validYearSemester.setValid(false);
            currentYearSemesterOfArrangementRepository.save(validYearSemester);
        }

        // Clear all existing arrangements of classes.
        for (ClassroomEntity classroomEntity : classroomRepository.findAll()) {
            for (TimeSlotEntity timeSlotEntity : classroomEntity.getTimeSlots()) {
                timeSlotEntity.setClazz(null);
                timeSlotRepository.save(timeSlotEntity);
            }
        }

        // Persist the new one.
        CurrentYearSemesterOfArrangementEntity newYearSemester = new
                CurrentYearSemesterOfArrangementEntity(yearSemester.getYear(), yearSemester.getSemester(), true);
        currentYearSemesterOfArrangementRepository.save(newYearSemester);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public YearSemester getCurrentYearSemesterOfArrangement() {
        List<CurrentYearSemesterOfArrangementEntity> validYearSemesters = currentYearSemesterOfArrangementRepository
                .findByValid(true);
        if (validYearSemesters.size() == 0) {
            throw new CurrentYearSemesterOfArrangementNotFoundException();
        }
        return new YearSemester(validYearSemesters.get(0));
    }
}
