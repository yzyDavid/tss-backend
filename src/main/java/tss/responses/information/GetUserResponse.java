package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class GetUserResponse {
    private final @Nls
    String status;
    private final String uid;
    private final String name;
    private final Integer type;
    private final String email;
    private final String telephone;
    private final String intro;

    public GetUserResponse(String status, String uid, String name, int type, String email,
                           String telephone, String intro) {
        this.status = status;
        this.uid = uid;
        this.name = name;
        this.type = type;
        this.email = email;
        this.telephone = telephone;
        this.intro = intro;
    }

    public String getStatus() {
        return status;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getIntro() {
        return intro;
    }
}
