package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class DeleteClassesResponse {
    @Nls private final String status;
    private final List<Long> failIds;

    public DeleteClassesResponse(String status, List<Long>failIds) {
        this.status = status;
        this.failIds = failIds;
    }

    public String getStatus() {
        return status;
    }

    public List<Long> getFailIds() {
        return failIds;
    }
}
