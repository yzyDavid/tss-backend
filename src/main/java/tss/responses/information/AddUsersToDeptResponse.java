package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class AddUsersToDeptResponse {
    @Nls
    private final String status;
    private final String department;
    private final String uid;

    public AddUsersToDeptResponse(String status, String department, String uid) {
        this.status = status;
        this.department = department;
        this.uid = uid;
    }

    public String getDepartment() {
        return department;
    }

    public String getUid() {
        return uid;
    }

    public String getStatus() {
        return status;
    }
}
