package tss.requests.information.bbs;

public class SearchSectionRequest {
    private String key;

    public SearchSectionRequest(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
