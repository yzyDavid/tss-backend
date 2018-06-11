package tss.responses.information;

import org.jetbrains.annotations.Nls;

/**
 * @author yzy
 */
public class AddUserResponse {
    private final @Nls
    String status;
    private final String uid;
    private final String name;
    private final String gender;
    private final String type;

    public AddUserResponse(String status, String uid, String name, String gender, String type) {
        this.status = status;
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.type = type;
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

    public String getGender() {
        return gender;
    }

    public String getType() {
        return type;
    }
}
