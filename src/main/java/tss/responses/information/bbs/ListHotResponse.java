package tss.responses.information.bbs;

import java.util.List;

public class ListHotResponse {
    private final List<String> boardNames;
    private final List<String> titles;
    private final List<String> authors;
    private final List<String> boardids;
    private final List<String> topicids;
    private final List<String> times;
    private final List<String> replyNUMs;
    private final List<String> lastReplyTimes;

    public ListHotResponse(List<String> boardNames, List<String> titles, List<String> authors, List<String> boardids, List<String> topicids, List<String> times, List<String> replyNUMs, List<String> lastReplyTimes) {
        this.boardNames = boardNames;
        this.titles = titles;
        this.authors = authors;
        this.boardids = boardids;
        this.topicids = topicids;
        this.times = times;
        this.replyNUMs = replyNUMs;
        this.lastReplyTimes = lastReplyTimes;
    }

    public List<String> getBoardNames() {
        return boardNames;
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getBoardids() {
        return boardids;
    }

    public List<String> getTopicids() {
        return topicids;
    }

    public List<String> getTimes() {
        return times;
    }

    public List<String> getReplyNUMs() {
        return replyNUMs;
    }

    public List<String> getLastReplyTimes() {
        return lastReplyTimes;
    }
}
