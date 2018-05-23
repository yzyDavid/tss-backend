package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class GetUserByUidResponse {
    private final @Nls
    String status;
    private final String uid;
    private final String name;
    private final String type;
    private final String email;
    private final String telephone;
    private final String intro;

    public GetUserByUidResponse(String status, String uid, String name, String type, String email,
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

    public String getType() {
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
