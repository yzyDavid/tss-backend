package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "paper")

public class PapersEntity {
    private String pid;
    private String begin;
    private String end;
    private String last;
    private String count;
    private String papername;
    private boolean isauto;

    private Set<PaperContainsQuestionEntity> paperquestion = new HashSet<>();


    private long answerednum;
    private double average;

    @Id
    @Column(name = "paper_pid", length = 10)
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }


    @Column(name = "paper_begin")
    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    @Column(name = "paper_end")
    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Column(name = "paper_last")
    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Column(name = "paper_count")
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Column(name = "paper_papername")
    public String getPapername() {
        return papername;
    }

    public void setPapername(String papername) {
        this.papername = papername;
    }

    @Column(name = "paper_isauto")
    public boolean getIsauto() {
        return isauto;
    }

    public void setIsauto(boolean isauto) {
        this.isauto = isauto;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paper")
    public Set<PaperContainsQuestionEntity> getPaperquestion() {
        return paperquestion;
    }

    public void setPaperquestion(Set<PaperContainsQuestionEntity> paperquestion) {
        this.paperquestion = paperquestion;
    }

    @Column(name = "paper_answerednum")
    public long getAnswerednum() {
        return answerednum;
    }

    public void setAnswerednum(long answerednum) {
        this.answerednum = answerednum;
    }

    @Column(name = "paper_average")
    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
