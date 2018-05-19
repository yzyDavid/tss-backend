package tss.requests.information;

public class DeleteResultRequest {

    private String Sid;
    private String Pid;

    public String getSid() {
        return Sid;
    }
    public void setSid(String sid) {
        Sid = sid;
    }
    public String getPid() {
        return Pid;
    }
    public void setPid(String pid) {
        Pid = pid;
    }
}
