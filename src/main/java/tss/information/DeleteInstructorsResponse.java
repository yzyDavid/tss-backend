package tss.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class DeleteInstructorsResponse {
    @Nls private final String status;

    public DeleteInstructorsResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
