package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class QueryUsersResponse {
    @Nls
    private final String status;
    private final List<String> uids;
    private final List<String> names;
    private final List<String> departments;
    private final List<String> genders;
    private final List<String> types;
    private final List<Integer> years;

    public QueryUsersResponse(String status, List<String> uids, List<String> names, List<String> departments,
                              List<String> genders, List<String> types, List<Integer> years) {
        this.status = status;
        this.uids = uids;
        this.names = names;
        this.departments = departments;
        this.genders = genders;
        this.types = types;
        this.years = years;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getUids() {
        return uids;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public List<String> getNames() {
        return names;
    }

    public List<Integer> getYears() {
        return years;
    }

    public List<String> getGenders() {
        return genders;
    }

    public List<String> getTypes() {
        return types;
    }
}
