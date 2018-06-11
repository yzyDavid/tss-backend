package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddMajorClassResponse {
    @Nls
    final private String status;
    final private String name;
    final private String major;

    public AddMajorClassResponse(String status, String name, String major) {
        this.status = status;
        this.name = name;
        this.major = major;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }
}
