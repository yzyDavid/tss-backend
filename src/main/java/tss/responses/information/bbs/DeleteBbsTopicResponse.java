package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;
import tss.requests.information.bbs.DeleteBbsTopicRequest;

public class DeleteBbsTopicResponse {
    @Nls
    private final String status;

    public DeleteBbsTopicResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
