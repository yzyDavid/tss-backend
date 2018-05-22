package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetInfoBbsSectionResponse {

    private final List<String> ids;
    private final List<String> names;

    public GetInfoBbsSectionResponse(List<String> ids, List<String> names){
        this.ids = ids;
        this.names = names;
    }

    public List<String> getIds() {
        return ids;
    }

    public List<String> getNames() {
        return names;
    }
}
