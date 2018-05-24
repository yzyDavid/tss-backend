package tss.information.untapped;

import tss.information.PaperResponseStruct;

import java.util.Date;

public class StartExamResponse {

    private final String status;
    private final PaperResponseStruct paperInfo;
    private final String StartTime;

    public StartExamResponse(String status, PaperResponseStruct paperInfo, String startTime) {
        this.status = status;
        this.paperInfo = paperInfo;
        StartTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public PaperResponseStruct getPaperInfo() {
        return paperInfo;
    }

    public String getStartTime() {
        return StartTime;
    }
}
