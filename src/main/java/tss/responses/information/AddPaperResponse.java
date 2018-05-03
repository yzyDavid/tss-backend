package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddPaperResponse {
    private @Nls
    final String status;
    private final String pid;

    public AddPaperResponse(String status, String pid){
        this.status = status;
        this.pid = pid;
    }

    public String getStatus() {return status;}

    public String getQid() {return pid;}
}
