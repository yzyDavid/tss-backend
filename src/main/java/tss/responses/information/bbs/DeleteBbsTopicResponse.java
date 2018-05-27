package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;
import tss.requests.information.bbs.DeleteBbsTopicRequest;

public class DeleteBbsTopicResponse {
    @Nls
    private final String status;
    private final long id;
    private final String name;
    private final String authorName;

    public DeleteBbsTopicResponse(String status, long id, String name, String authorName){
        this.status = status;
        this.id = id;
        this.name = name;
        this.authorName = authorName;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthorName() {
        return authorName;
    }
}
