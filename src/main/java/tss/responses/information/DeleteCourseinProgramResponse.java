package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeleteCourseinProgramResponse {
    @Nls
    final String status;
    private final String cid;
    private final String cname;
    private final String uid;

    public DeleteCourseinProgramResponse(String status, String cid, String cname, String uid) {
        this.status = status;
        this.cid = cid;
        this.cname = cname;
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public String getUid() {
        return uid;
    }

    public String getCname() {
        return cname;
    }

    public String getCid() {
        return cid;
    }
}
