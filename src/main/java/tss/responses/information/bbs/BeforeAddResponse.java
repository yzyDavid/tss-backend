package tss.responses.information.bbs;

public class BeforeAddResponse {
    private final String boardName;

    public BeforeAddResponse(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return boardName;
    }
}
