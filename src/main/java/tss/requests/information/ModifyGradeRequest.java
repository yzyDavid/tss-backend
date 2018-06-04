package tss.requests.information;

public class ModifyGradeRequest {
    private String uid;
    private long cid;
    private int score;
    private String studentid;
    private String reasons;

    public String getUid() {
        return uid;
    }

    public long getCid() {
        return cid;
    }

//    public ModifyGradeRequest(String uid, long cid, int score, String reasons) {
//        this.uid = uid;
//        this.cid = cid;
//        this.score = score;
//        this.reasons = reasons;
//    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getStudentid() {

        return studentid;
    }

    public int getScore() {

        return score;

    }

    public String getReasons() {
        return reasons;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }


    public void setReasons(String reasons) {
        this.reasons = reasons;
    }
}
