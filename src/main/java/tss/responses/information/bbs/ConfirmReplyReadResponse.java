package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

public class ConfirmReplyReadResponse {
    @Nls
    private final String status;

    public ConfirmReplyReadResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
