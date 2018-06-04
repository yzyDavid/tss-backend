package tss.entities;

import javax.persistence.*;

/**
 * @author reeve
 */
@Entity
@Table(name = "class_registration")
public class ClassRegistrationEntity {

    @EmbeddedId
    private ClassRegistrationId id;

    @Column(name = "score")
    private Integer score;
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }

    @ManyToOne(optional = false)
    @MapsId("studentId")
    private UserEntity student;

    @ManyToOne(optional = false)
    @MapsId("classId")
    private ClassEntity clazz;

    @Column
    private ClassStatusEnum status;

    public ClassRegistrationEntity() {
    }

    public ClassRegistrationEntity(Integer score, UserEntity student, ClassEntity clazz, ClassStatusEnum status) {
        this.id = new ClassRegistrationId(student.getUid(), clazz.getId());
        this.score = score;
        this.student = student;
        this.clazz = clazz;
        this.status = status;
    }

    public ClassStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ClassStatusEnum classStatusEnum) {
        status = classStatusEnum;
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

    public ClassRegistrationId getId() {
        return id;
    }

    public void setId(ClassRegistrationId id) {
        this.id = id;
    }
}
