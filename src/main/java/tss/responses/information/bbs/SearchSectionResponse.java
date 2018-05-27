package tss.responses.information.bbs;

import java.util.List;

public class SearchSectionResponse {
    private final List<String> boardNames;
    private final List<String> boardIDs;

    public SearchSectionResponse(List<String> boardIDs, List<String> boardNames) {
        this.boardNames = boardNames;
        this.boardIDs = boardIDs;
    }

    public List<String> getBoardNames() {
        return boardNames;
    }

    public List<String> getBoardIDs() {
        return boardIDs;
    }
}

