package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeletePaperResponse {
    @Nls
    final String status;

    public DeletePaperResponse(String status){
        this.status = status;
    }

    public String getStatus() {return status;}
}
