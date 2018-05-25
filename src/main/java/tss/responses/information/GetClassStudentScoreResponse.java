package tss.responses.information;

import java.util.ArrayList;
import java.util.List;

public class GetClassStudentScoreResponse {
    private String status;
    private List<String> students = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<Integer> score = new ArrayList<>();

    public GetClassStudentScoreResponse(String status) {
        this.status = status;
    }

    public GetClassStudentScoreResponse(String status, List<String> students, List<String> name, List<Integer> score) {
        this.status = status;
        this.students = students;
        this.name = name;
        this.score = score;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public String getStatus() {

        return status;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }

    public List<Integer> getScore() {

        return score;
    }

    public List<String> getStudents() {
        return students;
    }

    public List<String> getName() {
        return name;
    }
}
