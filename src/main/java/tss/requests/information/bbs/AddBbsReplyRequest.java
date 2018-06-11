package tss.requests.information.bbs;

public class AddBbsReplyRequest {
    private String tid;
    private String text;
    private String quoteIndex;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getQuoteIndex() {
        return quoteIndex;
    }

    public void setQuoteIndex(String quoteIndex) {
        this.quoteIndex = quoteIndex;
    }
}
