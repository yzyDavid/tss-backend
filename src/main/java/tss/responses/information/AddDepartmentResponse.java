package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddDepartmentResponse {
    @Nls
    private final String status;
    private final String name;

    public AddDepartmentResponse(String status, String name) {
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
