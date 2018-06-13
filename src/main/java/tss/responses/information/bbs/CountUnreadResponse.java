package tss.responses.information.bbs;

public class CountUnreadResponse {
    private final String letterNum;
    private final String replyNum;

    public CountUnreadResponse(String letterNum, String replyNum) {
        this.letterNum = letterNum;
        this.replyNum = replyNum;
    }

    public String getLetterNum() {
        return letterNum;
    }

    public String getReplyNum() {
        return replyNum;
    }
}
