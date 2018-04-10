package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddTeachesResponse {
    @Nls private final String status;
    private final String failUid;

    public AddTeachesResponse(String status, String uid) {
        this.status = status;
        this.failUid = uid;
    }

    public String getStatus() {
        return status;
    }

    public String getFailUid() {
        return failUid;
    }
}
