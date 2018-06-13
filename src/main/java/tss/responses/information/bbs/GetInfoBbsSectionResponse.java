package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetInfoBbsSectionResponse {

    private final List<String> boardIDs;
    private final List<String> boardNames;

    public GetInfoBbsSectionResponse(List<String> boardIDs, List<String> boardNames) {
        this.boardIDs = boardIDs;
        this.boardNames = boardNames;
    }

    public List<String> getBoardIDs() {
        return boardIDs;
    }

    public List<String> getBoardNames() {
        return boardNames;
    }
}
