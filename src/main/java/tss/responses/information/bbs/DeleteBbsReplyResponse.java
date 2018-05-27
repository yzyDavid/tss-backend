package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.Date;

public class DeleteBbsReplyResponse {
    @Nls
    private final String staus;
    private final long id;
    private final String authorName;

    public DeleteBbsReplyResponse(String staus, long id, String authorName) {
        this.staus = staus;
        this.id = id;
        this.authorName = authorName;
    }

    public String getStaus() {
        return staus;
    }

    public long getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }
}
