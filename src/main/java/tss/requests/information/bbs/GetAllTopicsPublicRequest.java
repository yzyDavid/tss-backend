package tss.requests.information.bbs;

public class GetAllTopicsPublicRequest {
    private String boardID;

    public GetAllTopicsPublicRequest(String boardID) {
        this.boardID = boardID;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }
}
