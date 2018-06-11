package tss.models;

import org.jetbrains.annotations.NotNull;
import tss.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author reeve
 */
public class Clazz {
    private Long id;
    private Integer year;
    private SemesterEnum semester;
    private Integer capacity;
    private Integer numStudent;

    private String teacherId;

    private String courseId;
    private String courseName;
    private Float courseCredit;
    private Integer numLessonsEachWeek;
    private String courseIntro;

    private List<TimeSlot> arrangements;
    private Integer numLessonsLeft;

    public Clazz() {
    }

    public Clazz(@NotNull ClassEntity classEntity) {
        id = classEntity.getId();
        year = classEntity.getYear();
        semester = classEntity.getSemester();
        capacity = classEntity.getCapacity();
        numStudent = classEntity.getNumStudent();

        teacherId = classEntity.getTeacher().getUid();

        CourseEntity courseEntity = classEntity.getCourse();
        courseId = courseEntity.getId();
        courseName = courseEntity.getName();
        courseCredit = courseEntity.getCredit();
        numLessonsEachWeek = courseEntity.getNumLessonsEachWeek();
        courseIntro = courseEntity.getIntro();

        arrangements = new ArrayList<>();
        numLessonsLeft = numLessonsEachWeek;
        for (TimeSlotEntity timeSlotEntity : classEntity.getTimeSlots()) {
            arrangements.add(new TimeSlot(timeSlotEntity));
            numLessonsLeft -= timeSlotEntity.getType().getSize();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Float getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(Float courseCredit) {
        this.courseCredit = courseCredit;
    }

    public Integer getNumLessonsEachWeek() {
        return numLessonsEachWeek;
    }

    public void setNumLessonsEachWeek(Integer numLessonsEachWeek) {
        this.numLessonsEachWeek = numLessonsEachWeek;
    }

    public String getCourseIntro() {
        return courseIntro;
    }

    public void setCourseIntro(String courseIntro) {
        this.courseIntro = courseIntro;
    }

    public List<TimeSlot> getArrangements() {
        return arrangements;
    }

    public void setArrangements(List<TimeSlot> arrangements) {
        this.arrangements = arrangements;
    }

    public Integer getNumLessonsLeft() {
        return numLessonsLeft;
    }

    public void setNumLessonsLeft(Integer numLessonsLeft) {
        this.numLessonsLeft = numLessonsLeft;
    }
}
