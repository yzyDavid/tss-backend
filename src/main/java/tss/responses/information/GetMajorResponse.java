package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class GetMajorResponse {
    @Nls
    final private String status;
    final private String name;

    public GetMajorResponse(String status, String name) {
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
