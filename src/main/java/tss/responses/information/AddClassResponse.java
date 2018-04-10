package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddClassResponse {
    @Nls
    private final String status;

    public AddClassResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
