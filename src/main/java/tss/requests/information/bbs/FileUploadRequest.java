package tss.requests.information.bbs;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadRequest {
    private byte[] bytes;
    private String name;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
