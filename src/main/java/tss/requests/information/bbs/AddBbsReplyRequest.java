package tss.requests.information.bbs;

public class AddBbsReplyRequest {
    private final long tid;
    private final String text;
    private final Integer quoteIndex;

    public AddBbsReplyRequest(long tid, String text, Integer quoteIndex) {
        this.tid = tid;
        this.text = text;
        this.quoteIndex = quoteIndex;
    }

    public long getTid() {
        return tid;
    }

    public String getText() {
        return text;
    }

    public Integer getQuoteIndex() {
        return quoteIndex;
    }
}
