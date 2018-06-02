package tss.models;

import tss.entities.SemesterEnum;
import tss.entities.CurrentYearSemesterOfArrangementEntity;

/**
 * @author reeve
 */
public class YearSemester {
    private Integer year;
    private SemesterEnum semester;

    public YearSemester() {
    }

    public YearSemester(CurrentYearSemesterOfArrangementEntity currentYearSemesterOfArrangementEntity) {
        this.year = currentYearSemesterOfArrangementEntity.getYear();
        this.semester = currentYearSemesterOfArrangementEntity.getSemester();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public SemesterEnum getSemester() {
        return semester;
    }

    public void setSemester(SemesterEnum semester) {
        this.semester = semester;
    }
}

