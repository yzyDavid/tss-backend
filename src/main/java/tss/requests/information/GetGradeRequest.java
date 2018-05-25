package tss.requests.information;

import java.util.List;

public class GetGradeRequest {
    private String uid;
    private List<Long> cid;

    public GetGradeRequest(String uid, List<Long> cid) {
        this.uid = uid;
        this.cid = cid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCid(List<Long> cid) {
        this.cid = cid;
    }

    public String getUid() {

        return uid;
    }

    public List<Long> getCid() {
        return cid;
    }
}
