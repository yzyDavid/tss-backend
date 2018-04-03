package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeleteInstructorsResponse {
    @Nls
    private final String status;

    public DeleteInstructorsResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
