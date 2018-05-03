package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class ModifyPaperResponse {
    private @Nls
    final String status;
    private final String pid;

    public ModifyPaperResponse(String status, String pid){
        this.status = status;
        this.pid = pid;
    }

    public String getStatus() {return status;}

    public String getPid() {return pid;}
}
