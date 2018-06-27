package tss.requests.information;

import org.springframework.web.multipart.MultipartFile;

public class ModifyPhotoRequest {
    private byte[] file;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
