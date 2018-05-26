package tss.information.untapped;

import java.util.List;

public class ShowPapersResponse {
    private final String status;
    private final List<String> Pid;

    public ShowPapersResponse(String status, List<String> pid) {
        this.status = status;
        Pid = pid;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getPid() {
        return Pid;
    }
}
