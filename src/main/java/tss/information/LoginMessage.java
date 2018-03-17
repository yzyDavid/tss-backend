package tss.information;

/**
 * @author yzy
 */
public class LoginMessage {
    private final String uid;
    private final String password;

    public LoginMessage(String uid, String password) {
        this.uid = uid;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public String getPassword() {
        return password;
    }
}
