package tss.responses.information;

import java.util.ArrayList;
import java.util.List;

public class GetClassStudentScoreResponse {
    private String status;
    private List<String> coursenames;
    private List<String> courseid;
    private List<Integer> scores;
    private List<Long> classids;

    public GetClassStudentScoreResponse(String status) {
        this.status = status;
    }

    public GetClassStudentScoreResponse(String status, List<String> coursenames, List<String> courseid, List<Integer> scores, List<Long> classids) {
        this.status = status;
        this.coursenames = coursenames;
        this.courseid = courseid;
        this.scores = scores;
        this.classids = classids;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getCoursenames() {
        return coursenames;
    }

    public List<String> getCourseid() {
        return courseid;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public List<Long> getClassids() {
        return classids;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCoursenames(List<String> coursenames) {
        this.coursenames = coursenames;
    }

    public void setCourseid(List<String> courseid) {
        this.courseid = courseid;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public void setClassids(List<Long> classids) {
        this.classids = classids;
    }
}
