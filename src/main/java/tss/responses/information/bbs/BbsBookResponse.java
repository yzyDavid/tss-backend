package tss.responses.information.bbs;

public class BbsBookResponse {
    private final String status;

    public BbsBookResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
