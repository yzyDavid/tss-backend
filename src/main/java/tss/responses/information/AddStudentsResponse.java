package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.Set;

public class AddStudentsResponse {
    @Nls
    private final String status;
    private final Set<String> failUids;

    public AddStudentsResponse(String status, Set<String> failUids) {
        this.status = status;
        this.failUids = failUids;
    }

    public String getStatus() {
        return status;
    }

    public Set<String> getFailUids() {
        return failUids;
    }
}
