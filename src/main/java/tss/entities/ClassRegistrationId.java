package tss.entities;

import org.apache.catalina.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author reeve
 */
@Embeddable
public class ClassRegistrationId implements Serializable {

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private UserEntity student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "class_id")
    private ClassEntity clazz;

    public ClassRegistrationId() {
        super();
    }

    public ClassRegistrationId(UserEntity student, ClassEntity clazz) {
        this.student = student;
        this.clazz = clazz;
    }

    @Column(name = "student_id", nullable = false)
    public UserEntity getStudent() {
        return student;
    }

    public void setStudent(UserEntity student) {
        this.student = student;
    }

    @Column(name = "class_id", nullable = false)
    public ClassEntity getClazz() {
        return clazz;
    }

    public void setClazz(ClassEntity clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassRegistrationId)) return false;
        ClassRegistrationId that = (ClassRegistrationId) o;
        return Objects.equals(getStudent(), that.getStudent()) &&
                Objects.equals(getClazz(), that.getClazz());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getStudent(), getClazz());
    }
}
