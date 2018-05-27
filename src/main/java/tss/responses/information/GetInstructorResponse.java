package tss.responses.information;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GetInstructorResponse {
    @Nls
    private final String status;
    @Nullable
    private final String tid;
    @Nullable
    private final String name;

    public GetInstructorResponse(String status, @Nullable String tid, @Nullable String name) {
        this.status = status;
        this.tid = tid;
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public String getTid() {
        return tid;
    }

    public String getName() {
        return name;
    }
}
