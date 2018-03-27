package tss.information;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;


import java.io.InputStream;

public class GetInfoResponse {
    private final @Nls
    String status;
    @Nullable
    private final String uid;
    @Nullable
    private final String name;
    @Nullable
    private final Integer type;
    @Nullable
    private final String email;
    @Nullable
    private final String telephone;
    @Nullable
    private final String intro;

    GetInfoResponse(String status, final UserEntity user) {
        this.status = status;
        if(user != null) {
            uid = user.getUid();
            name = user.getName();
            type = user.getType();
            email = user.getEmail();
            telephone = user.getTelephone();
            intro = user.getIntro();
            }
        else {
            uid = null;
            name = null;
            type = null;
            email = null;
            telephone = null;
            intro = null;
        }
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
