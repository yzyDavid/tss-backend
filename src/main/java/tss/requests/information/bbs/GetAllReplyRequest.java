package tss.requests.information.bbs;

public class GetAllReplyRequest {
    private String tid;
    private String page;

    public GetAllReplyRequest(String tid, String page) {
        this.tid = tid;
        this.page = page;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
