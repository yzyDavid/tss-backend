package tss.entities;

import javax.persistence.*;
import java.util.Date;


public class HistoryGradeEntity {
    private String Hid;
    private UserEntity Sid;
    private PapersEntity Pid;
    private double Grade;
    private Date StartTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String getHid() {
        return Hid;
    }
    public void setHid(String hid) {
        Hid = hid;
    }

    @JoinColumn(name = "StudentID")
    @ManyToOne
    public UserEntity getSid() {
        return Sid;
    }
    public void setSid(UserEntity sid) {
        Sid = sid;
    }

    @JoinColumn(name = "PaperID")
    @ManyToOne
    public PapersEntity getPid() {
        return Pid;
    }
    public void setPid(PapersEntity pid) {
        Pid = pid;
    }

    @Column (name = "Grade")
    public double getGrade() {
        return Grade;
    }
    public void setGrade(double grade) {
        Grade = grade;
    }

    @Column(name = "StartTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return StartTime;
    }
    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

}
