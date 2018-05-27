package tss.requests.information;

public class DeleteCourseinProgramRequest {
    private String cid;
    private String pid;

    public String getCid() {
        return cid;
    }
    public String getPid() { return pid; }

    public void setCid(String cid) {
        this.cid = cid;
    }
    public void setPid() {this.pid = pid;}
}
