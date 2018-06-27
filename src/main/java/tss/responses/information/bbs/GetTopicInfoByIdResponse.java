package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.Date;

public class GetTopicInfoByIdResponse {
    @Nls
    private final String status;
    private final long id;
    private final String name;
    private final String content;
    private final Date time;
    private final String authorName;
    private final String sectionName;
    private final int replyNum;

    public GetTopicInfoByIdResponse(String status, long id, String name, String content,
                                    Date time, String authorName, String sectionName, int replyNum) {
        this.status = status;
        this.id = id;
        this.name = name;
        this.content = content;
        this.time = time;
        this.authorName = authorName;
        this.sectionName = sectionName;
        this.replyNum = replyNum;
    }

    public String getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Date getTime() {
        return time;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public int getReplyNum() {
        return replyNum;
    }
}
