package tss.responses.information.bbs;

import java.util.List;

public class BbsShowBookResponse {
    private final List<String> boardNames;
    private final List<String> boardIDs;

    public BbsShowBookResponse(List<String> boardNames, List<String> boardIDs) {
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
