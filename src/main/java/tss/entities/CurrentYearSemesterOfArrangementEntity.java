package tss.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author reeve
 */
@Entity
@Table(name = "current_year_semester_of_arrangement")
public class CurrentYearSemesterOfArrangementEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private int year;

    private SemesterEnum semester;

    private boolean valid;

    public CurrentYearSemesterOfArrangementEntity() {
    }

    public CurrentYearSemesterOfArrangementEntity(int year, SemesterEnum semester, boolean valid) {
        this.year = year;
        this.semester = semester;
        this.valid = valid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public SemesterEnum getSemester() {
        return semester;
    }

    public void setSemester(SemesterEnum semester) {
        this.semester = semester;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
