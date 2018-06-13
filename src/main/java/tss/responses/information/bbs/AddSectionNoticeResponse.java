package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

public class AddSectionNoticeResponse {
    @Nls
    private final String status;

    public AddSectionNoticeResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
