package tss.responses.information;

public class ProcessModifyResponse {
    private String status;

    public ProcessModifyResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
