package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeleteStudentsResponse {
    @Nls
    private final String status;

    public DeleteStudentsResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
