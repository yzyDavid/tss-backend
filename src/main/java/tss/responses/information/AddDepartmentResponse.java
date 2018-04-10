package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddDepartmentResponse {
    @Nls private  final String status;

    public AddDepartmentResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
