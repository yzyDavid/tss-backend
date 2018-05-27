package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetGradeResponse {
    @Nls
    private final String status;
    private final List<String> qid;
    private final List<Double> rate;
    private final List<String> pid;
    private final List<String> score;

    public GetGradeResponse(String status, List<String> qid, List<Double> rate, List<String> pid, List<String> score) {
        this.status = status;
        this.qid = qid;
        this.rate = rate;
        this.pid = pid;
        this.score = score;
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
}
