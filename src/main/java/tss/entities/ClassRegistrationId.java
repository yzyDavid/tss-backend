package tss.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author reeve
 */
@Embeddable
public class ClassRegistrationId implements Serializable {

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "class_id")
    private Long classId;

    public ClassRegistrationId() {
    }

    public ClassRegistrationId(String studentId, Long classId) {
        this.studentId = studentId;
        this.classId = classId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClassRegistrationId that = (ClassRegistrationId) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(classId, that.classId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, classId);
    }
}
