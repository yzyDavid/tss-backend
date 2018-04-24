package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

public class DeleteBbsSectionResponse {
    @Nls
    private String status;
    private long id;
    private String name;

    public DeleteBbsSectionResponse(String status, long id, String name){
        this.status = status;
        this.id = id;
        this.name = name;
    }
}
