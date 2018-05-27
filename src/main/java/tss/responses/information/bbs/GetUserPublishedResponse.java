package tss.responses.information.bbs;

import java.util.List;

public class GetUserPublishedResponse {
    private final String userName;
    private final List<String> titles;
    private final List<String> times;
    private final List<String> topicIDs;
    private final List<String> boardIDs;
    private final List<String> boardNames;
    private final String currentPage;
    private final String totalPage;

    public GetUserPublishedResponse(String userName, List<String> titles, List<String> times, List<String> topicIDs, List<String> boardIDs, List<String> boardNames, String currentPage, String totalPage) {
        this.userName = userName;
        this.titles = titles;
        this.times = times;
        this.topicIDs = topicIDs;
        this.boardIDs = boardIDs;
        this.boardNames = boardNames;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getTimes() {
        return times;
    }

    public List<String> getTopicIDs() {
        return topicIDs;
    }

    public List<String> getBoardIDs() {
        return boardIDs;
    }

    public List<String> getBoardNames() {
        return boardNames;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public String getTotalPage() {
        return totalPage;
    }
}
