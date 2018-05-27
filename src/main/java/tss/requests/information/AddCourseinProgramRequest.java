package tss.requests.information;

public class AddCourseinProgramRequest {
    private String cid;
    private String pid;
    private String uid;
    private Integer type;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public Integer getType() {
        return type;
    }

    public String getUid() {
        return uid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
