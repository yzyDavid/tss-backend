package tss.requests.information.bbs;

public class AddBbsReplyRequest {
    private long tid;
    private String text;
    private Integer quoteIndex;

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getQuoteIndex() {
        return quoteIndex;
    }

    public void setQuoteIndex(Integer quoteIndex) {
        this.quoteIndex = quoteIndex;
    }
}
