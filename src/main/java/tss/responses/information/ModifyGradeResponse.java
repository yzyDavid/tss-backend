package tss.responses.information;

public class ModifyGradeResponse {
    private String status;

    public ModifyGradeResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
