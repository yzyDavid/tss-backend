package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetDepartmentResponse {
    @Nls
    private final String status;
    private final String name;
    private final List<String> majors;

    public GetDepartmentResponse(String status, String name, List<String> majors) {
        this.status = status;
        this.name = name;
        this.majors = majors;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public List<String> getMajors() {
        return majors;
    }
}
