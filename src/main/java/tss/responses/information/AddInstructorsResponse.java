package tss.responses.information;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class AddInstructorsResponse {
    @Nls
    private final String status;
    @Nullable
    private final Set<String> failUids;

    public AddInstructorsResponse(String status, Set<String> failUids) {
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
