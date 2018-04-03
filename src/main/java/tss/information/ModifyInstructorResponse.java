package tss.information;

import org.jetbrains.annotations.Nls;

public class ModifyInstructorResponse {
    @Nls
    private final String status;

    public ModifyInstructorResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
