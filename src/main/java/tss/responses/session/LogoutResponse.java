package tss.responses.session;

import org.jetbrains.annotations.Nls;

public class LogoutResponse {
    @Nls
    final private String status;
    final private String uid;

    public LogoutResponse(String status, String uid) {
        this.status = status;
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public String getUid() {
        return uid;
    }
}
