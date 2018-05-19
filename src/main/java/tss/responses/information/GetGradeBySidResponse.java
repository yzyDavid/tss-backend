package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetGradeBySidResponse {

    @Nls
    private final String status;
    private final String Sid;
    private final List<String> Pid;
    private final List<String> score;

    public GetGradeBySidResponse(String status, String sid, List<String> pid, List<String> score) {
        this.status = status;
        this.Sid = sid;
        this.Pid = pid;
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public String getSid() {
        return Sid;
    }

    public List<String> getPid() {
        return Pid;
    }

    public List<String> getScore() {
        return score;
    }

}
