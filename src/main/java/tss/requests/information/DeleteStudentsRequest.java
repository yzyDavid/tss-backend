package tss.requests.information;

import java.util.Set;

public class DeleteStudentsRequest {
    private String cid;
    private String tid;
    private Set<String> sid;

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

    public Set<String> getSid() {
        return sid;
    }

    public void setSid(Set<String> sid) {
        this.sid = sid;
    }
}
