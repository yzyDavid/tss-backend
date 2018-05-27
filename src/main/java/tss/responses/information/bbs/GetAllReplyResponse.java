package tss.responses.information.bbs;


import java.util.List;

public class GetAllReplyResponse {
    private final String title;
    private final String totalPage;
    private final String currentPage;
    private final String postTime;
    private final String boardName;
    private final String boardID;
    private final String topicID;
    private final List<String> ids;
    private final List<String> texts;
    private final List<String> quotes;
    private final List<String> times;
    private final List<String> photos;
    private final List<String> indexs;
    private final List<String> quoteAuthors;
    private final List<String> quoteTimes;
    private final List<String> quoteIndexs;

    public GetAllReplyResponse(String title, String totalPage, String currentPage, String postTime, String boardName, String boardID, String topicID, List<String> ids, List<String> texts, List<String> quotes, List<String> times, List<String> photos, List<String> indexs, List<String> quoteAuthors, List<String> quoteTimes, List<String> quoteIndexs) {
        this.title = title;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.postTime = postTime;
        this.boardName = boardName;
        this.boardID = boardID;
        this.topicID = topicID;
        this.ids = ids;
        this.texts = texts;
        this.quotes = quotes;
        this.times = times;
        this.photos = photos;
        this.indexs = indexs;
        this.quoteAuthors = quoteAuthors;
        this.quoteTimes = quoteTimes;
        this.quoteIndexs = quoteIndexs;
    }

    public String getTitle() {
        return title;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public String getPostTime() {
        return postTime;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getBoardID() {
        return boardID;
    }

    public String getTopicID() {
        return topicID;
    }

    public List<String> getIds() {
        return ids;
    }

    public List<String> getTexts() {
        return texts;
    }

    public List<String> getQuotes() {
        return quotes;
    }

    public List<String> getTimes() {
        return times;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public List<String> getIndexs() {
        return indexs;
    }

    public List<String> getQuoteAuthors() {
        return quoteAuthors;
    }

    public List<String> getQuoteTimes() {
        return quoteTimes;
    }

    public List<String> getQuoteIndexs() {
        return quoteIndexs;
    }
}
