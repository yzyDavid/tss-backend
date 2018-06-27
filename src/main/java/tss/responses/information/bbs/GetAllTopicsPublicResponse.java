package tss.responses.information.bbs;

import java.util.List;

public class GetAllTopicsPublicResponse {
    private final String boardName;
    private final String boardID;
    private final String boardText;

    private final List<String> topTitles;
    private final List<String> topAuthors;
    private final List<String> topTimes;
    private final List<String> topReplys;
    private final List<String> topTopicIDs;
    private final List<String> topLastReplyTimes;
    private final String watched;

    public GetAllTopicsPublicResponse(String boardName, String boardID, String boardText, List<String> topTitles, List<String> topAuthors, List<String> topTimes, List<String> topReplys, List<String> topTopicIDs, List<String> topLastReplyTimes, String watched) {
        this.boardName = boardName;
        this.boardID = boardID;
        this.boardText = boardText;
        this.topTitles = topTitles;
        this.topAuthors = topAuthors;
        this.topTimes = topTimes;
        this.topReplys = topReplys;
        this.topTopicIDs = topTopicIDs;
        this.topLastReplyTimes = topLastReplyTimes;
        this.watched = watched;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getBoardID() {
        return boardID;
    }

    public String getBoardText() {
        return boardText;
    }

    public List<String> getTopTitles() {
        return topTitles;
    }

    public List<String> getTopAuthors() {
        return topAuthors;
    }

    public List<String> getTopTimes() {
        return topTimes;
    }

    public List<String> getTopReplys() {
        return topReplys;
    }

    public List<String> getTopTopicIDs() {
        return topTopicIDs;
    }

    public List<String> getTopLastReplyTimes() {
        return topLastReplyTimes;
    }

    public String getWatched() {
        return watched;
    }
}

