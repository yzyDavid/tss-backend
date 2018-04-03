package tss.requests.information;

import org.springframework.lang.Nullable;

public class ModifyUserRequest {
    private String uid;
    @Nullable
    private String email;
    @Nullable
    private String telephone;
    @Nullable
    private String intro;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

};
