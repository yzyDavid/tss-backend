package tss.information;

/**
 * @author yzy
 */
public class AddUserResponse {
    private final String status;
    private final String uid;
    private final String name;

    public AddUserResponse(String status, String uid, String name) {
        this.status = status;
        this.uid = uid;
        this.name = name;
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
}
