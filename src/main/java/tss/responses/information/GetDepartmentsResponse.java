package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetDepartmentsResponse {
    @Nls
    private final String status;
    private final List<String> names;

    public GetDepartmentsResponse(String status, List<String> deptName) {
        this.status = status;
        this.names = deptName;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getNames() {
        return names;
    }
}
