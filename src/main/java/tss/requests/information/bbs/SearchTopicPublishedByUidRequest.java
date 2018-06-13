package tss.requests.information.bbs;

public class SearchTopicPublishedByUidRequest {
    private String uid;
    private String page;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
