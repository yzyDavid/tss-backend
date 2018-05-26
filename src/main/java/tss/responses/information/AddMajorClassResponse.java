package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddMajorClassResponse {
    @Nls
    final private String status;
    final private String name;
    final private String department;

    public AddMajorClassResponse(String status, String name, String department) {
        this.status = status;
        this.name = name;
        this.department = department;
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
