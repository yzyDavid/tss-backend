package tss.requests.information.bbs;

public class ModifyReplyContentRequest {
    private final long id;
    private final String newContent;

    public ModifyReplyContentRequest(long id, String newContent) {
        this.id = id;
        this.newContent = newContent;
    }

    public long getId() {
        return id;
    }

    public String getNewContent() {
        return newContent;
    }
}
