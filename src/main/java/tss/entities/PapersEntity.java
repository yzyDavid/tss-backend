package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "paper", indexes = {
        @Index(name = "paper_Index", columnList = "paperid")
})
public class PapersEntity{
    private String pid;
    private String begin;
    private String end;

    private Set<String> questionnum = new HashSet<>();
    private Set<String> questionscore = new HashSet<>();

    private long answerednum;
    private Float average;
    @Id
    @Column(name = "paper_id", length = 10)
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Column(name = "paper_answerednum")
    public long getAnswerednum() {
        return answerednum;
    }

    public void setAnswerednum(long answerednum) {
        this.answerednum = answerednum;
    }

    @Column(name = "paper_average")
    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }


    @Column(name = "paper_begin")
    public String getBegin() { return begin; }

    public void setBegin(String Begin) {
        this.begin = begin;
    }

    @Column(name = "paper_end")
    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paper")
    public Set<String> getQuestionnum() {
        return questionnum;
    }

    public void setQuestionnum(Set<String> questionnum) {
        this.questionnum = questionnum;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paper")
    public Set<String> getQuestionscore() {
        return questionscore;
    }

    public void setQuestionscore(Set<String> questionscore) {
        this.questionscore = questionscore;
    }





}
