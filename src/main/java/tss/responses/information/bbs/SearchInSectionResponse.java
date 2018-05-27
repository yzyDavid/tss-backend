package tss.responses.information.bbs;


import java.util.List;

public class SearchInSectionResponse {
    private final String currentPage;
    private final String totalPage;
    private final List<String> titles;
    private final List<String> authors;
    private final List<String> times;
    private final List<String> boardNames;
    private final List<String> boardIDs;
    private final List<String> topicIDs;
    private final List<String> replyNums;

    public SearchInSectionResponse(String currentPage, String totalPage, List<String> titles, List<String> authors, List<String> times, List<String> boardNames, List<String> boardIDs, List<String> topicIDs, List<String> replyNums) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.titles = titles;
        this.authors = authors;
        this.times = times;
        this.boardNames = boardNames;
        this.boardIDs = boardIDs;
        this.topicIDs = topicIDs;
        this.replyNums = replyNums;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getAuthors() {
        return authors;
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

    public List<String> getTopicIDs() {
        return topicIDs;
    }

    public List<String> getReplyNums() {
        return replyNums;
    }
}
