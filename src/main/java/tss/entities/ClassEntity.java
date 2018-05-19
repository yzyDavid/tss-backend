package tss.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "class")
public class ClassEntity {
    private Long id;
    private Integer year;
    private Integer capacity;
    private Integer studentNum = 0;
    private CourseEntity course;
    private Set<TeachesEntity> teaches;
    private Set<TakesEntity> takes;
    private Set<SectionEntity> sections;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "classes")
    public Set<TeachesEntity> getTeaches() {
        return teaches;
    }

    public void setTeaches(Set<TeachesEntity> teaches) {
        this.teaches = teaches;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
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

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "_class")
    public Set<TakesEntity> getTakes() {
        return takes;
    }

    public void setTakes(Set<TakesEntity> takes) {
        this.takes = takes;
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        } else {
            return id.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass()) || id == null) {
            return false;
        } else {
            return (id.equals(((ClassEntity) obj).id));
        }
    }
}
