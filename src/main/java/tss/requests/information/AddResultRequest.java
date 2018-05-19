package tss.requests.information;

public class AddResultRequest {

    private String Sid;
    private String Pid;
    private String[] Qid;
    private String [] Ans;

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
    public String[] getQid() {
        return Qid;
    }
    public void setQid(String[] qid) {
        Qid = qid;
    }
    public String[] getAns() {
        return Ans;
    }
    public void setAns(String[] ans) {
        Ans = ans;
    }
}
