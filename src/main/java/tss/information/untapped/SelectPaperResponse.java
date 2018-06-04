package tss.information.untapped;

import tss.information.PaperResponseStruct;

public class SelectPaperResponse {

    private final String status;
    private final PaperResponseStruct paperInfo;

    public SelectPaperResponse(String status, PaperResponseStruct paperInfo) {
        this.status = status;
        this.paperInfo = paperInfo;
    }

    public String getStatus() {
        return status;
    }
    public PaperResponseStruct getPaperInfo() {
        return paperInfo;
    }

}
