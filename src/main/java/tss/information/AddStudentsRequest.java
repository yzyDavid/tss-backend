package tss.information;

import java.util.List;

public class AddStudentsRequest {
    private String cid;
    private String tid;
    private List<String> sid;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
    public List<String> getSid() {
        return sid;
    }

    public void setSid(List<String> sid) {
        this.sid = sid;
    }
}
