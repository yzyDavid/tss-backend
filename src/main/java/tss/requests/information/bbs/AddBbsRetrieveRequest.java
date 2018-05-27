package tss.requests.information.bbs;

public class AddBbsRetrieveRequest {
    private long id;
    private long receiverdId;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReceiverdId() {
        return receiverdId;
    }

    public void setReceiverdId(long receiverdId) {
        this.receiverdId = receiverdId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
