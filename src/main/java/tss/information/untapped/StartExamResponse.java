package tss.information.untapped;

import tss.information.PaperResponseStruct;
import tss.information.QuestionExamResponseStruct;

import java.util.Date;
import java.util.List;

public class StartExamResponse {

    private final String status;
    private final String pid;
    private final List<QuestionExamResponseStruct> questioninfo;
    private final String starttime;

    public StartExamResponse(String status, String pid, String starttime, List<QuestionExamResponseStruct> questioninfo) {
        this.status = status;
        this.pid = pid;
        this.starttime = starttime;
        this.questioninfo = questioninfo;
    }

    public String getStatus() {
        return status;
    }

    public String getPid() {
        return pid;
    }

    public String getStarttime() {
        return starttime;
    }

    public List<QuestionExamResponseStruct> getQuestioninfo() {
        return questioninfo;
    }
}
