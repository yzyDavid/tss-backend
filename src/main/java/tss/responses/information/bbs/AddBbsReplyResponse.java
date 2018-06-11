package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.Date;

public class AddBbsReplyResponse {
    private final String status;

    public AddBbsReplyResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
