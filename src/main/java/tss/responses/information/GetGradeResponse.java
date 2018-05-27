package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetGradeResponse {
    @Nls
    private final String status;
    private final List<String> Qid;
    private final List<Double> rate;
    private final List<String> pid;
    private final List<String> score;

    public GetGradeResponse(String status, List<String> qid, List<Double> rate, List<String> pid, List<String> score) {
        this.status = status;
        Qid = qid;
        this.rate = rate;
        this.pid = pid;
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getQid() {
        return Qid;
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
