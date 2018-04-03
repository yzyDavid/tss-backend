package tss.information;

import tss.information.untapped.ClassroomEntity;
import tss.information.untapped.TimeSlotEntity;

import javax.persistence.*;

@Entity
@Table(name = "section")
public class SectionEntity {
    private long id;
    private char semester;
    private int year;
    private TimeSlotEntity timeSlot;
    private ClassroomEntity classroom;
    private TeachesEntity teaches;
    private CourseEntity course;
    private TakesEntity takes;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public char getSemester() {
        return semester;
    }

    public void setSemester(char semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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
