package tss.requests.information.bbs;

public class AddBbsRetrieveRequest {
    private final long id;
    private final long receiverdId;
    private final String content;

    public AddBbsRetrieveRequest(long id, long receiverdId, String content) {
        this.id = id;
        this.receiverdId = receiverdId;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public long getReceiverdId() {
        return receiverdId;
    }

    public String getContent() {
        return content;
    }
}
