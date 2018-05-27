package tss.requests.information;

import org.jetbrains.annotations.NotNull;

public class BasicUserRequest {
    @NotNull
    String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
