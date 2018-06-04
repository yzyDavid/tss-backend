package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetMajorResponse {
    @Nls
    final private String status;
    final private String name;
    final private String department;
    final private List<String> classes;

    public GetMajorResponse(String status, String name, String department, List<String> classes) {
        this.status = status;
        this.name = name;
        this.classes = classes;
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public List<String> getClasses() {
        return classes;
    }
}
