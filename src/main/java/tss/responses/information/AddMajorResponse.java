package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddMajorResponse {
    @Nls
    final private String status;
    final private String name;
    final private String major;

    public AddMajorResponse(String status, String name, String major) {
        this.status = status;
        this.major = major;
        this.name = name;
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
