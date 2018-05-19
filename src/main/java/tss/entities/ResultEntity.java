package tss.entities;

import javax.persistence.*;

@Entity
@Table(name = "Results", indexes = {
        @Index(name = "StudentID_Index", columnList = "StudentID"),
        @Index(name = "PaperID_Index", columnList = "PaperID"),
        @Index(name = "QuestionID_Index", columnList = "QuestionID"),

})
public class ResultEntity {

    private String Rid;
    private UserEntity Sid;
    private PapersEntity Pid;
    private QuestionEntity Qid;
    private String Ans;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String getRid() {
        return Rid;
    }

    public void setRid(String rid) {
        Rid = rid;
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

    @JoinColumn(name = "QuestionID")
    @ManyToOne
    public QuestionEntity getQid() {
        return Qid;
    }

    public void setQid(QuestionEntity qid) {
        Qid = qid;
    }

    @Column(name = "Answer")
    public String getAns() {
        return Ans;
    }

    public void setAns(String ans) {
        Ans = ans;
    }
}
