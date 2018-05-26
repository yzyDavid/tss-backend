package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeleteMajorClassResponse {
    @Nls
    final private String status;
    final private String name;

    public DeleteMajorClassResponse(String status, String name) {
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
