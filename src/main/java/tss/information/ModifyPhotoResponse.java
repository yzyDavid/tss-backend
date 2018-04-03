package tss.information;

import org.jetbrains.annotations.Nls;

public class ModifyPhotoResponse {
    private final @Nls
    String status;

    ModifyPhotoResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
