package tss.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.entities.CurrentYearSemesterOfArrangementEntity;
import tss.exceptions.CurrentYearSemesterOfArrangementNotFoundException;
import tss.models.YearSemester;
import tss.repositories.CurrentYearSemesterOfArrangementRepository;

import java.util.List;

/**
 * @author reeve
 */
@RestController
@RequestMapping("/current-year-semester-of-arrangement")
public class CurrentYearSemesterOfArrangementController {

    private CurrentYearSemesterOfArrangementRepository currentYearSemesterOfArrangementRepository;

    public CurrentYearSemesterOfArrangementController(CurrentYearSemesterOfArrangementRepository currentYearSemesterOfArrangementRepository) {
        this.currentYearSemesterOfArrangementRepository = currentYearSemesterOfArrangementRepository;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setCurrentYearSemesterOfArrangement(@RequestBody YearSemester yearSemester) {
        List<CurrentYearSemesterOfArrangementEntity> validYearSemesters = currentYearSemesterOfArrangementRepository
                .findByValid(true);
        for (CurrentYearSemesterOfArrangementEntity validYearSemester : validYearSemesters) {
            validYearSemester.setValid(false);
            currentYearSemesterOfArrangementRepository.save(validYearSemester);
        }

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
