package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.Date;
import java.util.List;

public class SearchInSectionResponse {
    @Nls
    private final String status;
    private final List<Long> ids;
    private final List<String> authorNames;
    private final List<String> contents;
    private final List<Date> times;

    public SearchInSectionResponse(String status, List<Long> ids, List<String> authorNames, List<String> contents, List<Date> times) {
        this.status = status;
        this.ids = ids;
        this.authorNames = authorNames;
        this.contents = contents;
        this.times = times;
    }

    public String getStatus() {
        return status;
    }

    public List<Long> getIds() {
        return ids;
    }

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public List<String> getContents() {
        return contents;
    }

    public List<Date> getTimes() {
        return times;
    }
}
