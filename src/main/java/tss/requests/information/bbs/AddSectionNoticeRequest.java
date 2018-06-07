package tss.requests.information.bbs;

public class AddSectionNoticeRequest {
    private String boardID;
    private String boardText;

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public String getBoardText() {
        return boardText;
    }

    public void setBoardText(String boardText) {
        this.boardText = boardText;
    }
}
