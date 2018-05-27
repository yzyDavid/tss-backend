package tss.requests.information.bbs;


public class ModifyTopicContentRequest {
    private long id;
    private String newContent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }
}
