package tss.requests.information.bbs;

public class DeleteBbsReplyRequest {
    private final long id;

    public DeleteBbsReplyRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
