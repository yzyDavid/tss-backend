package tss.information.untapped;

import tss.information.PaperResponseStruct;

import java.util.Date;

public class StartExamResponse {

    private final String status;
    private final PaperResponseStruct paperInfo;
    private final String starttime;

    public StartExamResponse(String status, PaperResponseStruct paperInfo, String starttime) {
        this.status = status;
        this.paperInfo = paperInfo;
        this.starttime = starttime;
    }

    public String getStatus() {
        return status;
    }

    public PaperResponseStruct getPaperInfo() {
        return paperInfo;
    }

    public String getStarttime() {
        return starttime;
    }
}
