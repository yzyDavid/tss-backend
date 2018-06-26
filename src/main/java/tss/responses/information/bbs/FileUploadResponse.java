package tss.responses.information.bbs;

public class FileUploadResponse {
    private final String status;

    public FileUploadResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
