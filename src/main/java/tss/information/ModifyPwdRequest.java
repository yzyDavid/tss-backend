package tss.information;

public class ModifyPwdRequest {
    private String uid;
    private String oldPwd;
    private String newPwd;

    ModifyPwdRequest(String uid, String oldPwd, String newPwd) {
        this.uid = uid;
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }


}

