package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mingqi Yi
 */
@Entity
@Table(name = "course", indexes = {
        @Index(name = "course_name_index", columnList = "course_name")
})
public class CourseEntity {
    private String cid;
    private String name;
    private Float credit;
    private String semester;
    private String intro;
    private DepartmentEntity department;
    private Set<ClassEntity> classes = new HashSet<>();
    private Set<TeachesEntity> teaches = new HashSet<>();

    @Id
    @Column(name = "course_id", length = 10)
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Column(name = "course_name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "course_credit")
    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    @Column(name = "course_semester", length = 8)
    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Column(name = "course_intro", length = 200)
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    public Set<ClassEntity> getClasses() {
        return classes;
    }

    public void setClasses(Set<ClassEntity> classes) {
        this.classes = classes;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    public Set<TeachesEntity> getTeaches() {
        return teaches;
    }

    public void setTeaches(Set<TeachesEntity> teaches) {
        this.teaches = teaches;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "department_id")
    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
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

