package tss.responses.information;

import org.jetbrains.annotations.Nls;
import tss.entities.QuestionEntity;

import java.util.List;

public class GetGradeResponse {
    @Nls
    private final String status;
    private final List<String> qid;
    private final List<Double> rate;
    private final List<String> pid;
    private final List<String> score;
    private final List<String> date;
    private final List<QuestionEntity> questions;


    public GetGradeResponse(String status, List<String> qid, List<Double> rate, List<String> pid, List<String> score, List<String> date, List<QuestionEntity> questions) {
        this.status = status;
        this.qid = qid;
        this.rate = rate;
        this.pid = pid;
        this.score = score;
        this.date = date;
        this.questions = questions;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getQid() {
        return qid;
    }

    public List<Double> getRate() {
        return rate;
    }

    public List<String> getPid() {
        return pid;
    }

    public List<String> getScore() {
        return score;
    }

    public List<String> getDate() {return date;}

    public List<QuestionEntity> getQuestions() {return questions;}
}

