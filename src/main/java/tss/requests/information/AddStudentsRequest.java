package tss.requests.information;

import java.util.Set;

public class AddStudentsRequest {
    private String cid;
    private String tid;
    private Set<String> sids;

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

    public Set<String> getSids() {
        return sids;
    }

    public void setSids(Set<String> sids) {
        this.sids = sids;
    }
}
