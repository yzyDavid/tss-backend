package tss.requests.information.bbs;

public class DeleteBbsSectionRequest {
    private final long id;

    public DeleteBbsSectionRequest(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
