package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeleteUserResponse {
    private final @Nls
    String status;
    private final String uid;
    private final String name;

    public DeleteUserResponse(String status, String uid, String name) {
        this.status = status;
        this.uid = uid;
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

}
