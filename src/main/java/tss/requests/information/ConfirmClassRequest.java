package tss.requests.information;

import java.util.Objects;

public class ConfirmClassRequest {
    private String uid;
    private Long classId;

    public ConfirmClassRequest(String uid, Long classId) {
        this.uid = uid;
        this.classId = classId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfirmClassRequest)) return false;
        ConfirmClassRequest that = (ConfirmClassRequest) o;
        return Objects.equals(getUid(), that.getUid()) &&
                Objects.equals(getClassId(), that.getClassId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUid(), getClassId());
    }
}
