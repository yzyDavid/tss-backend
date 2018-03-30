package tss.information;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "instructor")
public class InstructorEntity {
    private long id;
    private UserEntity teacher;
    private CourseEntity course;
    private Integer date;
    private Integer beginTime;
    private Integer duration;
    private String classroom; //TODO: replace it with class Classroom
    private Set<TakesEntity> takes = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @JoinColumn(name = "teacher_id")
    public UserEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(UserEntity teacher) {
        this.teacher = teacher;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instructor")
    public Set<TakesEntity> getTakes() {
        return takes;
    }

    public void setTakes(Set<TakesEntity> takes) {
        this.takes = takes;
    }

    public void addTake(TakesEntity take) {
        takes.add(take);
    }

    @Column(name = "date")
    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        if(date >= 0 && date < 7)
            this.date = date;
    }

    @Column(name = "begin_time")
    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    @Column(name = "duaration")
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Column(name = "classroom")
    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
