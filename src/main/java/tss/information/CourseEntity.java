package tss.information;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class CourseEntity {
    private String cid;
    private String name;
    private Float credit;
    private Character semester;
    private String intro;
    private Integer capacity;
    private Set<TeachesEntity> teaches = new HashSet<>();
    private Set<SectionEntity> sections = new HashSet<>();
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
    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    @Column(name = "course_semester", nullable = false)
    public Character getSemester() {
        return semester;
    }

    public void setSemester(Character semester) {
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
    public Set<TeachesEntity> getTeaches() {
        return teaches;
    }

    public void setTeaches(Set<TeachesEntity> teaches) {
        this.teaches = teaches;
    }

    public void addTeaches(TeachesEntity instructor) {
        teaches.add(instructor);
    }

    public void deleteTeaches(Set<String> uids) {
        for(TeachesEntity instructor : teaches)
            if(uids.contains(instructor.getTeacher().getUid()))
                teaches.remove(instructor);
    }

    public TeachesEntity findTeachesByUid(String uid) {
        for(TeachesEntity instructor : teaches)
            if(uid.equals(instructor.getTeacher().getUid()))
                return instructor;
        return null;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    public Set<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionEntity> sections) {
        this.sections = sections;
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

