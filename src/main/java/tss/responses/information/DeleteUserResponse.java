package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeleteUserResponse {
    private final @Nls
    String status;
    private final String[] uids;

    public DeleteUserResponse(String status, String[] uids) {
        this.status = status;
        this.uids = uids;
    }

    public String getStatus() {
        return status;
    }

    public String[] getUids() {
        return uids;
    }
}
