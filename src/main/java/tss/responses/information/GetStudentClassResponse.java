package tss.responses.information;

import java.util.List;

public class GetStudentClassResponse {
    private String status;
    private List<String> courses;
    private List<Long> cid;

    public GetStudentClassResponse(String status, List<String> courses, List<Long> cid) {
        this.status = status;
        this.courses = courses;
        this.cid = cid;
    }

    public GetStudentClassResponse(String status) {

        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public void setCid(List<Long> cid) {
        this.cid = cid;
    }

    public String getStatus() {

        return status;
    }

    public List<String> getCourses() {
        return courses;
    }

    public List<Long> getCid() {
        return cid;
    }
}
