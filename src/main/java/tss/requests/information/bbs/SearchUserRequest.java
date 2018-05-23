package tss.requests.information.bbs;

public class SearchUserRequest {
    private final String key;

    public SearchUserRequest(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
