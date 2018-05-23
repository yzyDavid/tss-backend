package tss.requests.information.bbs;

public class SearchInSectionRequest {
    private final String key;
    private final String page;

    public SearchInSectionRequest(String key, String page) {
        this.key = key;
        this.page = page;
    }

    public String getKey() {
        return key;
    }

    public String getPage() {
        return page;
    }
}
