package tss.requests.information;

import java.util.List;

public class AddUsersToMajorClassRequest {
    private String majorClass;
    private List<String> uids;

    public String getMajorClass() {
        return majorClass;
    }

    public void setUids(List<String> uids) {
        this.uids = uids;
    }

    public List<String> getUids() {
        return uids;
    }

    public void setMajorClass(String majorClass) {
        this.majorClass = majorClass;
    }
}
