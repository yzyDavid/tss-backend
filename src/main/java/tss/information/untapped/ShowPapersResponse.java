package tss.information.untapped;

import java.util.List;

public class ShowPapersResponse {
    private final String status;
    private final List<String> pid;

    public ShowPapersResponse(String status, List<String> pid) {
        this.status = status;
        this.pid = pid;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getPid() {
        return pid;
    }
}
