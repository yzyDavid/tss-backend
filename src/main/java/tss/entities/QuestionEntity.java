package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions", indexes = {
        @Index(name = "qid_Index", columnList = "qid")
})
public class QuestionEntity{
    private String qid;
    private String question;
    private String qanswer;
    private String qtype;
    private String qunit;

    private int answerednum;
    private double correct;

    @Id
    @Column(name = "question_qid", length = 10)
    public String getId() {
        return  qid;
    }

    public void setId(String qid) {
        this.qid = qid;
    }

    @Column(name = "question_question", length = 300)
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) { this.question = question; }

    @Column(name = "question_qanswer", length = 200)
    public String getQanswer() {
        return qanswer;
    }

    public void setQAanswer(String qanswer) {
        this.qanswer = qanswer;
    }

    @Column(name = "question_qtype")
    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    @Column(name = "question_qunit")
    public String getQunit() {
        return qunit;
    }

    public void setQunit(String qunit) {
        this.qunit = qunit;
    }

    @Column(name = "question_answerednum")
    public int getAnswerednum() {
        return answerednum;
    }

    public void setAnswerednum(int answerednum) {
        this.answerednum = answerednum;
    }

    @Column(name = "question_correct")
    public double getCorrect() {
        return correct;
    }

    public void setCorrect(double correct) {
        this.correct = correct;
    }

}