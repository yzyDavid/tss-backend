package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.Date;

public class AddBbsRetrieveResponse {
    @Nls
    private final String status;
    private final long id;
    private final String senderId;
    private final String  receiverId;
    private final String content;
    private final Date time;

    public AddBbsRetrieveResponse(String status, long id, String senderId, String receiverId, String content, Date time) {
        this.status = status;
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

    public Date getTime() {
        return time;
    }
}
