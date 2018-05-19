package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeleteQuestionResponse {
    @Nls
    final String status;

    public DeleteQuestionResponse(String status){
        this.status = status;
    }

    public String getStatus() {return status;}
}
