package tss.information;

import java.util.List;

public class DeleteInstructorsRequest {
    private String cid;
    private List<String> uid;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<String> getUid() {
        return uid;
    }

    public void setUid(List<String> uid) {
        this.uid = uid;
    }
}
