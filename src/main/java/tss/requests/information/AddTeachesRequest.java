package tss.requests.information;

import java.util.List;

public class AddTeachesRequest {
    String cid;
    List<String> uids;

    public List<String> getUids() {
        return uids;
    }

    public void setUids(List<String> uids) {
        this.uids = uids;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
