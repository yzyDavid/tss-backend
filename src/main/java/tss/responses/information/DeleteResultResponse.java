package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeleteResultResponse {

    @Nls
    private  final String status;

    public  DeleteResultResponse(String status) {
        this.status= status;
    }

    public String getStatus() {
        return status;
    }

}
