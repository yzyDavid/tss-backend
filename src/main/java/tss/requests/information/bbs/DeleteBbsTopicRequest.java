package tss.requests.information.bbs;

public class DeleteBbsTopicRequest {
    private final long id;

    public DeleteBbsTopicRequest(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
