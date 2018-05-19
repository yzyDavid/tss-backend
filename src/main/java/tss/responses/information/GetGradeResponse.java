package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetGradeResponse {
    @Nls
    private final String status;
    private final List<String> Qid;
    private final List<String> rate;
    public GetGradeResponse(String status, List<String> qid, List<String> rate) {
        this.status = status;
        Qid = qid;
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }
    public List<String> getQid() {
        return Qid;
    }
    public List<String> getRate() {
        return rate;
    }
}
