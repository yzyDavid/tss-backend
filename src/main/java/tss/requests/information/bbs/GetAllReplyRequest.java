package tss.requests.information.bbs;

public class GetAllReplyRequest {
    private final long tid;
    private final Integer pageNum;

    public GetAllReplyRequest(long tid, Integer pageNum) {
        this.tid = tid;
        this.pageNum = pageNum;
    }

    public long getTid() {
        return tid;
    }

    public Integer getPageNum() {
        return pageNum;
    }
}
