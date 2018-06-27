package tss.responses.information;

import java.util.ArrayList;
import java.util.List;

public class GetAllClassResponse {
    private String status;
    List<String> courses_name = new ArrayList<>();
    List<Long> class_id = new ArrayList<>();

    public GetAllClassResponse(String status) {
        this.status = status;
    }

    public GetAllClassResponse(String status, List<String> courses_name, List<Long> class_id) {
        this.status = status;
        this.courses_name = courses_name;
        this.class_id = class_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCourses_name(List<String> courses_name) {
        this.courses_name = courses_name;
    }

    public void setClass_id(List<Long> class_id) {
        this.class_id = class_id;
    }

    public String getStatus() {

        return status;
    }

    public List<String> getCourses_name() {
        return courses_name;
    }

    public List<Long> getClass_id() {
        return class_id;
    }
}
