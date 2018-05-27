package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Program refers to a list of courses that student must follow.
 *
 */

@Entity
@Table(name = "program")
public class ProgramEntity {
    String pid;
    String uid;
    private Set<UserEntity> students;
    private Set<CourseEntity> courses = new HashSet<>();

    @Id
    @Column(name = "program_id")
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }

    @Column(name = "uid")

    public String getUid() { return uid;}
    public void setUid(String uid) {this.uid = uid; }

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "program_course", joinColumns = {@JoinColumn(name = "program_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    public Set<CourseEntity> getCourses() { return courses;}

    public void setCourses(Set<CourseEntity> courses) {
        this.courses = courses;
    }

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    public Set<UserEntity> getStudents() { return students ;}

    public void setStudents(Set<UserEntity> students) {

        this.students = students;
    }

}
