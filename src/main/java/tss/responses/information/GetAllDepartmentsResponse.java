package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetAllDepartmentsResponse {
    @Nls private final String status;
    private final List<String> deptName;
    private final List<Short> deptId;

    public GetAllDepartmentsResponse(String status, List<Short> deptId, List<String> deptName) {
        this.status = status;
        this.deptName = deptName;
        this.deptId = deptId;
    }

    public String getStatus() {
        return status;
    }

    public List<Short> getDeptId() {
        return deptId;
    }

    public List<String> getDeptName() {
        return deptName;
    }
}
