package tss.entities;

import tss.information.untapped.ClassroomEntity;

import javax.persistence.*;

@Entity
@Table(name = "section")
public class SectionEntity {
    private long id;
    private Character semester;
    private Integer year;
    private TimeSlotEntity timeSlot;
    private ClassroomEntity classroom;
    private TeachesEntity teaches;
    private CourseEntity course;
    private TakesEntity takes;

    public SectionEntity(TeachesEntity teaches, CourseEntity course) {
        this.teaches = teaches;
        this.course = course;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Character getSemester() {
        return semester;
    }

    public void setSemester(Character semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "classroom_id")
    public ClassroomEntity getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomEntity classroom) {
        this.classroom = classroom;
    }

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "time_slot_id")
    public TimeSlotEntity getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlotEntity timeSlot) {
        this.timeSlot = timeSlot;
    }

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "teaches_id")
    public TeachesEntity getTeaches() {
        return teaches;
    }

    public void setTeaches(TeachesEntity teaches) {
        this.teaches = teaches;
    }

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "course_id")
    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "takes_id")
    public TakesEntity getTakes() {
        return takes;
    }

    public void setTakes(TakesEntity takes) {
        this.takes = takes;
    }
}
