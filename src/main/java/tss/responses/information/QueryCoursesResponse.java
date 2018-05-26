package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class QueryCoursesResponse {
    @Nls
    private final String status;
    private final List<String> cids;
    private final List<String> names;
    private final List<String> departments;

    public QueryCoursesResponse(String status, List<String> cids, List<String> names, List<String> departments) {
        this.status = status;
        this.cids = cids;
        this.names = names;
        this.departments = departments;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getCids() {
        return cids;
    }

    public List<String> getNames() {
        return names;
    }

    public List<String> getDepartments() {
        return departments;
    }
}
