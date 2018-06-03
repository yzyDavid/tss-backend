package tss.requests.information;

import java.util.List;

public class AddUsersToDeptRequest {
    List<String> uids;
    String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getUids() {
        return uids;
    }

    public void setUids(List<String> uids) {
        this.uids = uids;
    }
}
