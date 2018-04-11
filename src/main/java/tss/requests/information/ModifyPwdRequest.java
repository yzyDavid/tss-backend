package tss.requests.information;

public class ModifyPwdRequest {
    private String uid;
    private String oldPwd;
    private String newPwd;

    public String getUid() {
        return uid;
    }

    void setUid(String uid) {
        this.uid = uid;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }


}

