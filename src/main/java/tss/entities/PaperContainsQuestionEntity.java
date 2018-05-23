package tss.entities;

import javax.persistence.*;

@Entity
@Table(name = "contains")
public class PaperContainsQuestionEntity {
    private String id;
    private PapersEntity paper;
    private QuestionEntity question;
    private String score;

    @Id
    @Column(name = "contains_id", length = 10)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "paper_pid")
    public PapersEntity getPaper() {
        return paper;
    }

    public void setPaper(PapersEntity paper) {
        this.paper = paper;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "question_qid")
    public QuestionEntity getQuestion() {
        return question;
    }


    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    @Column(name = "contains_score")
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
