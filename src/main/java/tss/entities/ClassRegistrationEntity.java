package tss.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author reeve
 */
@Entity
@Table(name = "class_registration")
public class ClassRegistrationEntity {
    @Id
    @Column(name = "crid")
    private String crid;

    @Column(name = "score")
    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private UserEntity student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "class_id")
    private ClassEntity clazz;

    @Enumerated(EnumType.STRING)
    private ClassStatusEnum status;

    @Column(name = "register_time")
    private Timestamp selectTime;

    @Column(name = "confirm_time")
    private Timestamp confirmTime;

    public ClassRegistrationEntity() {

    }

    public ClassRegistrationEntity(Integer score, UserEntity student, ClassEntity clazz, String crid, ClassStatusEnum status,
                                   Timestamp selectTime, Timestamp confirmTime) {
        this.crid = crid;
        this.score = score;
        this.status = status;
        this.student = student;
        this.clazz = clazz;
        this.selectTime = selectTime;
        this.confirmTime = confirmTime;
    }

    public ClassStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ClassStatusEnum classStatusEnum) {
        status = classStatusEnum;
    }


    public String getCrid() {
        return crid;
    }

    public void setCrid(String crid) {
        this.crid = crid;
    }

    public UserEntity getStudent() {
        return student;
    }

    public void setClazz(ClassEntity clazz) {
        this.clazz = clazz;
    }

    public void setStudent(UserEntity student) {

        this.student = student;
    }

    public ClassEntity getClazz() {
        return clazz;
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
