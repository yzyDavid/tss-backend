package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddResultResponse {
    @Nls
    private  final String status;

    public  AddResultResponse(String status) {
        this.status= status;
    }

    public String getStatus() {
        return status;
    }

}
