package tss.responses.information.bbs;

import java.util.List;

public class SearchUserResponse {
    private final List<String> userNames;
    private final List<String> userIDs;
    private final List<String> photoURLs;

    public SearchUserResponse(List<String> userNames, List<String> userIDs, List<String> photoURLs) {
        this.userNames = userNames;
        this.userIDs = userIDs;
        this.photoURLs = photoURLs;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public List<String> getUserIDs() {
        return userIDs;
    }

    public List<String> getPhotoURLs() {
        return photoURLs;
    }
}
