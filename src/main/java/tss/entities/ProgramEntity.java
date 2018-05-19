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
    short pid;
    private Set<UserEntity> students;
    private Set<CourseEntity> courses = new HashSet<>();

    @Id
    @Column(name = "program_id")
    @GeneratedValue(strategy = GenerationType.AUTO)

    public short getId() {
        return pid;
    }
    public void setId(short pid) {
        this.pid = pid;
    }

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "program_course", joinColumns = {@JoinColumn(name = "program_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    public Set<CourseEntity> getCourses() { return courses;}

    public void setCourses(Set<CourseEntity> courses) {
        this.courses = courses;
    }

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Set<UserEntity> getStudents() { return students ;}

    private void setStudents(Set<UserEntity> students) {
        for (UserEntity user : students)
        {
            // in case that the user is not student
            if (user.getType() != UserEntity.TYPE_STUDENT)
                return;
        }
        this.students = students;
    }

}
