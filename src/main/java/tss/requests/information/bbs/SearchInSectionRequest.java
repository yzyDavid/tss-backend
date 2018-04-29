package tss.requests.information.bbs;

public class SearchInSectionRequest {
    private final long id;
    private final String[] keyWords;

    public SearchInSectionRequest(long id, String[] keyWords) {
        this.id = id;
        this.keyWords = keyWords;
    }

    public long getId() {
        return id;
    }

    public String[] getKeyWords() {
        return keyWords;
    }
}
