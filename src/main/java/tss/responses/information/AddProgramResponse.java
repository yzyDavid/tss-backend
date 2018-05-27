package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddProgramResponse {
    private @Nls
    final String status;

    public AddProgramResponse(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
