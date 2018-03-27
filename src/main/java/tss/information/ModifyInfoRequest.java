package tss.information;

import org.springframework.lang.Nullable;

public class ModifyInfoRequest {
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

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIntro() {
        return intro;
    }

};
