package tss.responses.information.bbs;


public class GetAllReplyResponse {
    private final String title;
    private final String totalPage;
    private final String currentPage;
    private final String postTime;
    private final String boardName;
    private final String boardID;
    private final String topicID;
    private final String[] ids;
    private final String[] texts;
    private final String[] quotes;
    private final String[] times;
    private final String[] photos;
    private final String[] indexs;
    private final String[] quoteAuthors;
    private final String[] quoteTimes;
    private final String[] quoteIndexs;

    public GetAllReplyResponse(String title, String totalPage, String currentPage, String postTime, String boardName, String boardID, String topicID, String[] ids, String[] texts, String[] quotes, String[] times, String[] photos, String[] indexs, String[] quoteAuthors, String[] quoteTimes, String[] quoteIndexs) {
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

    public String[] getIds() {
        return ids;
    }

    public String[] getTexts() {
        return texts;
    }

    public String[] getQuotes() {
        return quotes;
    }

    public String[] getTimes() {
        return times;
    }

    public String[] getPhotos() {
        return photos;
    }

    public String[] getIndexs() {
        return indexs;
    }

    public String[] getQuoteAuthors() {
        return quoteAuthors;
    }

    public String[] getQuoteTimes() {
        return quoteTimes;
    }

    public String[] getQuoteIndexs() {
        return quoteIndexs;
    }
}
