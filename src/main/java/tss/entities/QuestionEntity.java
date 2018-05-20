package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question", indexes = {
        @Index(name = "question_qid_index", columnList = "question_qid"),
        @Index(name = "question_qtype_index", columnList = "question_qtype"),
        @Index(name = "question_qunit_index", columnList = "question_qunit"),
})
public class QuestionEntity {
    private String qid;
    private String question;
    private String qanswer;
    private String qtype;
    private String qunit;

    private int answerednum;
    private double correct;

    private Set<PaperContainsQuestionEntity> paperquestion = new HashSet<>();

    @Id
    @Column(name = "question_qid")
    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    @Column(name = "question_question", length = 300)
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Column(name = "question_qanswer", length = 200)
    public String getQanswer() {
        return qanswer;
    }

    public void setQanswer(String qanswer) {
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


    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "question")
    public Set<PaperContainsQuestionEntity> getPaperquestion() {
        return paperquestion;
    }

    public void setPaperquestion(Set<PaperContainsQuestionEntity> paperquestion) {
        this.paperquestion = paperquestion;
    }
}