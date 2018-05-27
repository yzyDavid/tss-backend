package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddGradeResponse {
    @Nls
    private final String status;

    public AddGradeResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
