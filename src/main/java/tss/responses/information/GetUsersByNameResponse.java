package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetUsersByNameResponse {
    @Nls
    private final String status;
    private final List<String> uids;

    public GetUsersByNameResponse(String status, List<String> uids) {
        this.status = status;
        this.uids = uids;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getUids() {
        return uids;
    }
}
