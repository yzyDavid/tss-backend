package tss.requests.information;

public class ModifyClassRegistrationRequest {
    private String uid;
    private Long classId;
    private Integer score;

    public ModifyClassRegistrationRequest(String uid, Long classId, Integer score) {
        this.uid = uid;
        this.classId = classId;
        this.score = score;
    }

    public ModifyClassRegistrationRequest() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
