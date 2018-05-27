package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.Date;

public class ReadBbsRetrieveResponse {
    @Nls
    private final String status;
    private final long id;
    private final String sid;
    private final String rid;
    private final String content;
    private final Date time;

    public ReadBbsRetrieveResponse(String status, long id, String sid, String rid, String content, Date time) {
        this.status = status;
        this.id = id;
        this.sid = sid;
        this.rid = rid;
        this.content = content;
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public String getSid() {
        return sid;
    }

    public String getRid() {
        return rid;
    }

    public String getContent() {
        return content;
    }

    public Date getTime() {
        return time;
    }
}
