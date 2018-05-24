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
    private UserEntity student;
    private PapersEntity paper;
    private int grade;
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
    public UserEntity getStudent() {
        return student;
    }

    public void setStudent(UserEntity student) {
        this.student = student;
    }

    @JoinColumn(name = "PaperID")
    @ManyToOne
    public PapersEntity getPaper() {
        return paper;
    }

    public void setPaper(PapersEntity paper) {
        this.paper = paper;
    }

    @Column(name = "Grade")
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
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


    /*

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }


    public UserEntity getSid() {
        return sid;
    }

    public void setSid(UserEntity sid) {
        this.sid = sid;
    }


    public PapersEntity getPid() {
        return pid;
    }

    public void setPid(PapersEntity pid) {
        this.pid = pid;
    }


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    */
}
