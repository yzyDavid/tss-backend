package tss.responses.information.bbs;

import java.util.List;

public class SearchTopicPublishedByUidResponse {
    private final String userName;
    private final String currentPage;
    private final String totalPage;
    private final List<String> times;
    private final List<String> boardNames;
    private final List<String> boardIDs;
    private final List<String> titles;
    private final List<String> topicIDs;

    public SearchTopicPublishedByUidResponse(String userName, String currentPage, String totalPage, List<String> times, List<String> boardNames, List<String> boardIDs, List<String> titles, List<String> topicIDs) {
        this.userName = userName;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.times = times;
        this.boardNames = boardNames;
        this.boardIDs = boardIDs;
        this.titles = titles;
        this.topicIDs = topicIDs;
    }

    public String getUserName() {
        return userName;
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

    public List<String> getBoardNames() {
        return boardNames;
    }

    public List<String> getBoardIDs() {
        return boardIDs;
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getTopicIDs() {
        return topicIDs;
    }
}
