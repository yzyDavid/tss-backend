package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeleteDepartmentResponse {
    @Nls
    final String status;
    final String name;

    public DeleteDepartmentResponse(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
