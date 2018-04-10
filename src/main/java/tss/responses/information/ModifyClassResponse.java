package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class ModifyClassResponse {
    @Nls
    final String status;

    public ModifyClassResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

