package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.Date;

public class AddBbsReplyResponse {
    @Nls
    private final String status;

    private final long id;
    private final String topicName;
    private final String content;
    private final Date time;

    public AddBbsReplyResponse(String status, long id, String name, String content, Date time){
        this.status = status;
        this.topicName = name;
        this.id = id;
        this.content = content;
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return topicName;
    }

    public String getContent() {
        return content;
    }

    public Date getTime() {
        return time;
    }
}
