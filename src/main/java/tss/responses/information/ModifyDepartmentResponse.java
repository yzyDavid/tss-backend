package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class ModifyDepartmentResponse {
    @Nls
    final String status;

    public ModifyDepartmentResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
