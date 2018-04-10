package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeleteDepartmentResponse {
    @Nls
    final String status;

    public DeleteDepartmentResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
