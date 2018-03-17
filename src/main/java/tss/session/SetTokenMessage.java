package tss.session;

/**
 * @author yzy
 */
public class SetTokenMessage {
    private final String uid;
    private final String token;

    public SetTokenMessage(String uid, String token) {
        this.uid = uid;
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public String getToken() {
        return token;
    }
}
