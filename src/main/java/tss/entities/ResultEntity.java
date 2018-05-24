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
    private UserEntity Student;
    private PapersEntity Paper;
    private QuestionEntity Question;
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
    public UserEntity getStudent() {
        return Student;
    }

    public void setStudent(UserEntity student) {
        Student = student;
    }

    @JoinColumn(name = "PaperID")
    @ManyToOne
    public PapersEntity getPaper() {
        return Paper;
    }

    public void setPaper(PapersEntity paper) {
        Paper = paper;
    }

    @JoinColumn(name = "QuestionID")
    @ManyToOne
    public QuestionEntity getQuestion() {
        return Question;
    }

    public void setQuestion(QuestionEntity question) {
        Question = question;
    }

    @Column(name = "Answer")
    public String getAns() {
        return Ans;
    }

    public void setAns(String ans) {
        Ans = ans;
    }


    /*

    public String getRid() {
        return Rid;
    }

    public void setRid(String rid) {
        Rid = rid;
    }


    public UserEntity getSid() {
        return Sid;
    }

    public void setSid(UserEntity sid) {
        Sid = sid;
    }


    public PapersEntity getPid() {
        return Pid;
    }

    public void setPid(PapersEntity pid) {
        Pid = pid;
    }


    public QuestionEntity getQid() {
        return Qid;
    }

    public void setQid(QuestionEntity qid) {
        Qid = qid;
    }


    public String getAns() {
        return Ans;
    }

    public void setAns(String ans) {
        Ans = ans;
    }

*/
}
