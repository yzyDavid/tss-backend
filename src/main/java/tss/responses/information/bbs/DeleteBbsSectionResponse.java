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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
