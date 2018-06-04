package tss.requests.information;

public class AddResultRequest {

    private String pid;
    private String[] qid;
    private String[] ans;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String[] getQid() {
        return qid;
    }

    public void setQid(String[] qid) {
        this.qid = qid;
    }

    public String[] getAns() {
        return ans;
    }

    public void setAns(String[] ans) {
        this.ans = ans;
    }
}
