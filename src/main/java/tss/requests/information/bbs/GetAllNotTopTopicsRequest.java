package tss.requests.information.bbs;

public class GetAllNotTopTopicsRequest {
    private String boardID;
    private String currentPage;


    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
