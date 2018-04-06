package tss.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "class")
public class ClassEntity {
    private long id;
    private Integer year;
    private Integer capacity;
    private Integer studentNum;
    private TeachesEntity teaches;
    private CourseEntity course;
    private Set<TakesEntity> takes;
    private Set<SectionEntity> sections;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Column(name = "student_number")
    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "_class")
    public Set<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionEntity> sections) {
        this.sections = sections;
    }

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH}, mappedBy = "_class")
    public Set<TakesEntity> getTakes() {
        return takes;
    }

    public void setTakes(Set<TakesEntity> takes) {
        this.takes = takes;
    }
}
