package tss.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "HistoryGrade", indexes = {
        @Index(name = "StudentID_Index", columnList = "StudentID")
       // @Index(name = "PaperID_Index", columnList = "PaperID")
})
public class HistoryGradeEntity {
    private String hid;
    private UserEntity student;
    private PapersEntity paper;
    private int grade;
    private Date starttime;

    @Id
    @Column(name = "Historyid", length = 100)
    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    @JoinColumn(name = "StudentID")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    public UserEntity getStudent() {
        return student;
    }

    public void setStudent(UserEntity student) {
        this.student = student;
    }

    @JoinColumn(name = "PaperID")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
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
    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }
}
