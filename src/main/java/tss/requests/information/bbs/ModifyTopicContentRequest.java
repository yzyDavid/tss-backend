package tss.requests.information.bbs;


public class ModifyTopicContentRequest {
    private final long id;
    private final String newContent;

    public ModifyTopicContentRequest(long id, String newContent){
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
