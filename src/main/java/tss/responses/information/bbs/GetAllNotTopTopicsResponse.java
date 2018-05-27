package tss.responses.information.bbs;

import java.util.List;

public class GetAllNotTopTopicsResponse {
    private final String currentPage;
    private final String totalPage;

    private final List<String> topicTitles;
    private final List<String> topicAuthors;
    private final List<String> topicTimes;
    private final List<String> topicReplys;
    private final List<String> topicIDs;
    private final List<String> topicLastReplyTimes;

    public GetAllNotTopTopicsResponse(String currentPage, String totalPage, List<String> topicTitles, List<String> topicAuthors, List<String> topicTimes, List<String> topicReplys, List<String> topicIDs, List<String> topicLastReplyTimes) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.topicTitles = topicTitles;
        this.topicAuthors = topicAuthors;
        this.topicTimes = topicTimes;
        this.topicReplys = topicReplys;
        this.topicIDs = topicIDs;
        this.topicLastReplyTimes = topicLastReplyTimes;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public List<String> getTopicTitles() {
        return topicTitles;
    }

    public List<String> getTopicAuthors() {
        return topicAuthors;
    }

    public List<String> getTopicTimes() {
        return topicTimes;
    }

    public List<String> getTopicReplys() {
        return topicReplys;
    }

    public List<String> getTopicIDs() {
        return topicIDs;
    }

    public List<String> getTopicLastReplyTimes() {
        return topicLastReplyTimes;
    }
}
