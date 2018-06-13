package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

public class SetTopicNotTopResponse {
    @Nls
    private final String status;

    public SetTopicNotTopResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
