package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class QueryUsersResponse {
    @Nls
    private final String status;
    private final List<String> uids;
    private final List<String> names;
    private final List<String> departments;

    public QueryUsersResponse(String status, List<String> uids, List<String> names, List<String> departments) {
        this.status = status;
        this.uids = uids;
        this.names = names;
        this.departments = departments;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getUids() {
        return uids;
    }
}
