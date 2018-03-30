package tss.information;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class CourseEntity {
    private String cid;
    private String name;
    private Integer credit;
    private Integer semester;
    private String intro;
    private Integer capacity;
    private Set<InstructorEntity> instructors = new HashSet<>();
    //private Integer examBeginTime;
    //private Integer examDuration;

    @Id
    @Column(name = "course_id", length = 10)
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Column(name = "course_name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "course_credit", nullable = false)
    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    @Column(name = "course_semester", nullable = false)
    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        if(0 <= semester && semester < 4)
            this.semester = semester;
    }

    @Column(name = "course_intro")
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Column(name = "course_capacity", nullable = false)
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    public Set<InstructorEntity> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<InstructorEntity> instructors) {
        this.instructors = instructors;
    }

    public void addInstructors(InstructorEntity instructor) {
        instructors.add(instructor);
    }

    /*public Integer getExamBeginTime() {
        return examBeginTime;
    }

    public void setExamBeginTime(Integer examBeginTime) {
        this.examBeginTime = examBeginTime;
    }

    public Integer getExamduration() {
        return examDuration;
    }

    public void setExamduration(Integer examDuration) {
        this.examDuration = examDuration;
    }*/
}

