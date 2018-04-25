package tss.requests.information.bbs;

public class AddBbsTopicRequest {
    private final long id;
    private final String name;
    private final long sectionId;
    private final String content;

    public AddBbsTopicRequest(long id, String name, long sectionId, String content){
        this.id = id;
        this.name = name;
        this.sectionId = sectionId;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getSectionId() {
        return sectionId;
    }

    public String getContent() {
        return content;
    }
}
