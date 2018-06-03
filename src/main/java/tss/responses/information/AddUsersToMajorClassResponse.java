package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddUsersToMajorClassResponse {
    @Nls private final String status;
    private final String majorClass;
    private final String uid;

    public AddUsersToMajorClassResponse(String status, String majorClass, String uid) {
        this.status = status;
        this.majorClass = majorClass;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getStatus() {
        return status;
    }

    public String getMajorClass() {
        return majorClass;
    }
}
