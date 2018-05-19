package tss.entities;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teaches")
public class TeachesEntity {
    private Long id;
    private CourseEntity course;
    private UserEntity teacher;
    private Set<ClassEntity> classes;

    public TeachesEntity(UserEntity user, CourseEntity course) {
        this.teacher = user;
        this.course = course;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    public UserEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(UserEntity teacher) {
        this.teacher = teacher;
    }

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "teaches_class", joinColumns = {@JoinColumn(name = "teaches_id")}, inverseJoinColumns = {@JoinColumn(name = "class_id")})
    public Set<ClassEntity> getClasses() {
        return classes;
    }

    public void setClasses(Set<ClassEntity> classes) {
        this.classes = classes;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
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
            return (id.equals(((TeachesEntity) obj).id));
        }
    }
}
