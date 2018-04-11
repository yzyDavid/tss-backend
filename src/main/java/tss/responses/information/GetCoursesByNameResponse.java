package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetCoursesByNameResponse {
    @Nls
    private final String status;
    private final List<String> cids;

    public GetCoursesByNameResponse(String status, List<String> cids) {
        this.status = status;
        this.cids = cids;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getCids() {
        return cids;
    }
}
