package tss.responses.session;

import org.jetbrains.annotations.Nls;

/**
 * @author yzy
 */
public class LoginResponse {
    private final String uid;
    private final String token;
    private final String type;
    private final @Nls
    String info;

    public LoginResponse(String uid, String token, String type, String info) {
        this.uid = uid;
        this.token = token;
        this.type = type;
        this.info = info;
    }

    public String getUid() {
        return uid;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }
}
