package tss.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "HistoryGrade", indexes = {
        @Index(name = "StudentID_Index", columnList = "StudentID"),
        @Index(name = "PaperID_Index", columnList = "PaperID")
})
public class HistoryGradeEntity {
    private String hid;
    private UserEntity sid;
    private PapersEntity pid;
    private double grade;
    private Date startTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    @JoinColumn(name = "StudentID")
    @ManyToOne
    public UserEntity getSid() {
        return sid;
    }

    public void setSid(UserEntity sid) {
        this.sid = sid;
    }

    @JoinColumn(name = "PaperID")
    @ManyToOne
    public PapersEntity getPid() {
        return pid;
    }

    public void setPid(PapersEntity pid) {
        this.pid = pid;
    }

    @Column(name = "Grade")
    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Column(name = "StartTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
