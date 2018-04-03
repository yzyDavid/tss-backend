package tss.information;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "instructor")
public class TeachesEntity {
    private long id;
    //private CompositeKeys id;
    private UserEntity teacher;
    private CourseEntity course;
    private Integer date;
    private Integer beginTime;
    private Integer duration;
    private String classroom; //TODO: replace it with class Classroom
    private Set<TakesEntity> takes = new HashSet<>();
    private Set<SectionEntity> sections = new HashSet<>();

    TeachesEntity(UserEntity user, CourseEntity course) {
        //this.id = new CompositeKeys(user.getUid(), course.getCid());
        this.teacher = user;
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

    /*@Id
    public CompositeKeys getId() {
        return id;
    }

    public void setId(CompositeKeys id) {
        this.id = id;
    }*/

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

    public void deleteTakes(Set<String> uids) {
        for(TakesEntity take : takes)
            if(uids.equals(take.getStudent().getUid()))
                takes.remove(take);
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teaches")
    public Set<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionEntity> sections) {
        this.sections = sections;
    }

    public void addSection(SectionEntity section) {
        this.sections.add(section);
    }

    public void deleteSection(Set<String> cids) {
        /*for(String cid : cids) {
            if()
        }*/
    }

}
