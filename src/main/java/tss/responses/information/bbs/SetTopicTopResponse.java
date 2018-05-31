package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

public class SetTopicTopResponse {
    @Nls
    private final String status;

    public SetTopicTopResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
