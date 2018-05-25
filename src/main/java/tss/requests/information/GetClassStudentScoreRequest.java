package tss.requests.information;

public class GetClassStudentScoreRequest {
    private String uid;
    private Long cid;

//    public GetClassStudentScoreRequest(String uid, Long cid) {
//        this.uid = uid;
//        this.cid = cid;
//    }

    public String getUid() {
        return uid;
    }

    public Long getCid() {
        return cid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }
}
