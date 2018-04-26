package tss.entities;

import javax.persistence.*;

/**
 * @author reeve
 */
@Entity
public class ClassRegistrationEntity {

    @EmbeddedId
    private ClassRegistrationId id;

    private Integer score;

    @ManyToOne(optional = false)
    @MapsId("studentId")
    private UserEntity student;

    @ManyToOne(optional = false)
    @MapsId("classId")
    private ClassEntity clazz;

    public ClassRegistrationEntity() {
    }

    public ClassRegistrationEntity(Integer score, UserEntity student, ClassEntity clazz) {
        this.id = new ClassRegistrationId(student.getUid(), clazz.getId());
        this.score = score;
        this.student = student;
        this.clazz = clazz;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public UserEntity getStudent() {
        return student;
    }

    public void setStudent(UserEntity student) {
        id.setStudentId(student.getUid());
        this.student = student;
    }

    public ClassEntity getClazz() {
        return clazz;
    }

    public void setClazz(ClassEntity clazz) {
        id.setClassId(clazz.getId());
        this.clazz = clazz;
    }
}
