package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddMajorResponse {
    @Nls
    final private String status;
    final private String name;
    final private String department;

    public AddMajorResponse(String status, String name, String department) {
        this.status = status;
        this.department = department;
        this.name = name;
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
}
