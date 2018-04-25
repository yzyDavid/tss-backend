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
    private String content;
    private String answer;
    private String type;
    private String unit;

    private Integar answerednum;
    private Float correct;

    @id
    @Column(name = "question_qid", length = 10)
    public String getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "question_content", length = 300)
    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }

    @Column(name = "question_answer", length = 200)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Column(name = "question_type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "question_unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name = "question_answerednum")
    public Integar getAnswerednum() {
        return answerednum;
    }

    public void setAnswerednum(Integar answerednum) {
        this.answerednum = answerednum;
    }

    @Column(name = "question_correct")
    public Float getCorrect() {
        return correct;
    }

    public void setCorrect(Float correct) {
        this.correct = correct;
    }

}