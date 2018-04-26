package tss.requests.information.bbs;

public class AddBbsReplyRequest {
    private final long id;
    private final long topicId;
    private final String content;

    public AddBbsReplyRequest(long id, long topic, String content) {
        this.id = id;
        this.topicId = topic;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public long getTopic() {
        return topicId;
    }

    public String getContent() {
        return content;
    }
}
