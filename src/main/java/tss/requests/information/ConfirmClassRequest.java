package tss.requests.information;

import java.util.Objects;

public class ConfirmClassRequest {
    private String uid;
    private Long classId;

    public String getUid() {
        return uid;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getClassId() {
        return classId;
    }
}
