package tss.entities;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author reeve
 */
@Entity
@Table(name = "class_registration")
public class ClassRegistrationEntity {

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

    @Column(name = "status")
    private ClassStatusEnum status;

    @Column(name = "register_time")
    private Timestamp selectTime;

    @Column(name = "confirm_time")
    private Timestamp confirmTime;

    public ClassRegistrationEntity() {
    }

    public ClassRegistrationEntity(Integer score, UserEntity student, ClassEntity clazz, ClassStatusEnum status,
                                   Timestamp selectTime, Timestamp confirmTime) {
        this.id = new ClassRegistrationId(student.getUid(), clazz.getId());
        this.score = score;
        this.student = student;
        this.clazz = clazz;
        this.status = status;
        this.selectTime = selectTime;
        this.confirmTime = confirmTime;
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

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "studentId", column = @Column(name = "studentId")),
            @AttributeOverride(name = "classId", column = @Column(name = "classId"))}
    )
    public ClassRegistrationId getId() {
        return this.id;
    }

    public void setId(ClassRegistrationId id) {
        this.id = id;
    }

    public Timestamp getSelectTime() {
        return this.selectTime;
    }
    public void setSelectTime(Timestamp selectTime) {
        this.selectTime = selectTime;
    }

    public Timestamp getConfirmTime() {
        return this.confirmTime;
    }
    public void setConfirmTime(Timestamp confirmTime) {
        this.confirmTime = confirmTime;
    }
}
