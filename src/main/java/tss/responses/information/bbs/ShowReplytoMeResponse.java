package tss.responses.information.bbs;

import java.util.List;

public class ShowReplytoMeResponse {
    private final String currentPage;
    private final String totalPage;
    private final List<String> times;
    private final List<String> userIDs;
    private final List<String> userNames;
    private final List<String> topicIDs;
    private final List<String> replyPos;
    private final List<String> reads;
    private final List<String> titles;

    public ShowReplytoMeResponse(String currentPage, String totalPage, List<String> times, List<String> userIDs, List<String> userNames, List<String> topicIDs, List<String> replyPos, List<String> reads, List<String> titles) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.times = times;
        this.userIDs = userIDs;
        this.userNames = userNames;
        this.topicIDs = topicIDs;
        this.replyPos = replyPos;
        this.reads = reads;
        this.titles = titles;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public List<String> getTimes() {
        return times;
    }

    public List<String> getUserIDs() {
        return userIDs;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public List<String> getTopicIDs() {
        return topicIDs;
    }

    public List<String> getReplyPos() {
        return replyPos;
    }

    public List<String> getReads() {
        return reads;
    }

    public List<String> getTitles() {
        return titles;
    }
}
