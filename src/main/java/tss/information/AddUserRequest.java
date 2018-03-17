package tss.information;

/**
 * @author yzy
 */
public class AddUserRequest {
    private String uid;
    private String name;
    private String password;

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
