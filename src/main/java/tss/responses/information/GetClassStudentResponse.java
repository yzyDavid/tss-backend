package tss.responses.information;

import java.util.ArrayList;
import java.util.List;

public class GetClassStudentResponse {
    private String status;
    private List<String> students = new ArrayList<>();
    private List<String> name = new ArrayList<>();

    public GetClassStudentResponse(String status) {
        this.status = status;
    }

    public GetClassStudentResponse(String status, List<String> students, List<String> name) {
        this.status = status;
        this.students = students;
        this.name = name;
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

    public List<String> getStudents() {
        return students;
    }

    public List<String> getName() {
        return name;
    }
}
