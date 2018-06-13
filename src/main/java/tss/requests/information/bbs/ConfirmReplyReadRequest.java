package tss.requests.information.bbs;

public class ConfirmReplyReadRequest {
    private String topicID;
    private String replyPos;

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public String getReplyPos() {
        return replyPos;
    }

    public void setReplyPos(String replyPos) {
        this.replyPos = replyPos;
    }
}
