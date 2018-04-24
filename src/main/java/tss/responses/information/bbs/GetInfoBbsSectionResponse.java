package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetInfoBbsSectionResponse {
    @Nls
    private final String status;
    private final List<Long> ids;
    private final List<String> names;

    public GetInfoBbsSectionResponse(String status, List<Long> ids, List<String> names){
        this.status = status;
        this.ids = ids;
        this.names = names;
    }

    public String getStatus() {
        return status;
    }

    public List<Long> getIds() {
        return ids;
    }

    public List<String> getNames() {
        return names;
    }
}
