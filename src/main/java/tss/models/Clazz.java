package tss.models;

import org.jetbrains.annotations.NotNull;
import tss.entities.ClassEntity;

/**
 * @author reeve
 */
public class Clazz {
    private long id;
    private Integer year;
    private String semester;
    private Integer capacity;
    private Integer numStudent;

    public Clazz() {
    }

    public Clazz(long id, Integer year, String semester, Integer capacity, Integer numStudent) {
        this.id = id;
        this.year = year;
        this.semester = semester;
        this.capacity = capacity;
        this.numStudent = numStudent;
    }

    public Clazz(@NotNull ClassEntity classEntity) {
        id = classEntity.getId();
        year = classEntity.getYear();
        semester = classEntity.getSemester();
        capacity = classEntity.getCapacity();
        numStudent = classEntity.getNumStudent();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getNumStudent() {
        return numStudent;
    }

    public void setNumStudent(Integer numStudent) {
        this.numStudent = numStudent;
    }
}
