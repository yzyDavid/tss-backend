package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class GetDepartmentResponse {
    @Nls private final String status;
    private final Short id;
    private final String name;

    public GetDepartmentResponse(String status, Short id, String name) {
        this.status = status;
        this.id = id;
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public Short getId() {
        return id;
    }
}
