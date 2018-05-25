package tss.responses.information;

import java.util.List;

public class GetGradeResponse {


    private String status;
    private List<Integer> scores;

    public GetGradeResponse(String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public String getStatus() {

        return status;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public GetGradeResponse(String status, List<Integer> scores) {

        this.status = status;
        this.scores = scores;
    }
}
