package tss.requests.information;

import tss.annotations.session.DataAccessControl;

public class GetUserByUidRequest {
    @DataAccessControl(entityName = "UserEntity", fieldName = "uid")
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
