package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddQuestionResponse {
    private @Nls
    final String status;
    private final String qid;

    public AddQuestionResponse(String status, String qid){
        this.status = status;
        this.qid = qid;
    }

    public String getStatus() {return status;}

    public String getQid() {return qid;}

}
