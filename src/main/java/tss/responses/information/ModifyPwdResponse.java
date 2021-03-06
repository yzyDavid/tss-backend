package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class ModifyPwdResponse {
    private final @Nls
    String status;
    private final String uid;
    private final String name;

    public ModifyPwdResponse(String status, String uid, String name) {
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
