package tss.entities;

import javax.persistence.*;

@Entity
@Table(name = "Results")
      //  @Index(name = "StudentID_Index", columnList = "StudentID"),
       // @Index(name = "PaperID_Index", columnList = "PaperID"),
       // @Index(name = "QuestionID_Index", columnList = "QuestionID"),

public class ResultEntity {

    private String rid;
    private UserEntity student;
    private PapersEntity paper;
    private QuestionEntity question;
    private String ans;

    @Id
    @Column(name = "Resultid", length = 100)
    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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

    @JoinColumn(name = "QuestionID")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    @Column(name = "Answer")
    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }


    /*

    public String getRid() {
        return Rid;
    }

    public void setRid(String rid) {
        Rid = rid;
    }


    public UserEntity getStudent() {
        return Student;
    }

    public void setStudent(UserEntity student) {
        Student = student;
    }


    public PapersEntity getPaper() {
        return Paper;
    }

    public void setPaper(PapersEntity paper) {
        Paper = paper;
    }


    public QuestionEntity getQuestion() {
        return Question;
    }

    public void setQuestion(QuestionEntity question) {
        Question = question;
    }


    public String getAns() {
        return Ans;
    }

    public void setAns(String ans) {
        Ans = ans;
    }
*/




}
