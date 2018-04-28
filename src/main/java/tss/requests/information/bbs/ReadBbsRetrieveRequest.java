package tss.requests.information.bbs;

public class ReadBbsRetrieveRequest {
    private final long id;

    public ReadBbsRetrieveRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
