package tss.requests.information;

import java.util.List;

public class AddGradeRequest {
    private String uid;
    private long cid;
    private List<String> studentid;
    private List<Integer> score;

//    public AddGradeRequest(String uid, long cid, List<String> studentid, List<Integer> score) {
//        this.uid = uid;
//        this.cid = cid;
//        this.studentid = studentid;
//        this.score = score;
//    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public void setStudentid(List<String> studentid) {
        this.studentid = studentid;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }

    public String getUid() {

        return uid;
    }

    public long getCid() {
        return cid;
    }

    public List<String> getStudentid() {
        return studentid;
    }

    public List<Integer> getScore() {
        return score;
    }
}
