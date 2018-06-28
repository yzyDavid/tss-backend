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

    /**
     *  topic information */
    private final String lzid;
    private final String lztext;
    private final String lzphoto;
    private final String lztime;
    private final String lzname;

    private final List<String> ids;
    private final List<String> texts;
    private final List<String> quotes;
    private final List<String> times;
    private final List<String> photos;
    private final List<String> indexs;
    private final List<String> quoteAuthors;
    private final List<String> quoteTimes;
    private final List<String> quoteIndexs;
    private final List<String> names;
    private final String top;

    public GetAllReplyResponse(String title, String totalPage, String currentPage, String postTime, String boardName, String boardID, String topicID, String lzid, String lztext, String lzphoto, String lztime, String lzname, List<String> ids, List<String> texts, List<String> quotes, List<String> times, List<String> photos, List<String> indexs, List<String> quoteAuthors, List<String> quoteTimes, List<String> quoteIndexs, List<String> names, String top) {
        this.title = title;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.postTime = postTime;
        this.boardName = boardName;
        this.boardID = boardID;
        this.topicID = topicID;
        this.lzid = lzid;
        this.lztext = lztext;
        this.lzphoto = lzphoto;
        this.lztime = lztime;
        this.lzname = lzname;
        this.ids = ids;
        this.texts = texts;
        this.quotes = quotes;
        this.times = times;
        this.photos = photos;
        this.indexs = indexs;
        this.quoteAuthors = quoteAuthors;
        this.quoteTimes = quoteTimes;
        this.quoteIndexs = quoteIndexs;
        this.names = names;
        this.top = top;
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

    public String getLzid() {
        return lzid;
    }

    public String getLztext() {
        return lztext;
    }

    public String getLzphoto() {
        return lzphoto;
    }

    public String getLztime() {
        return lztime;
    }

    public String getLzname() {
        return lzname;
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

    public List<String> getNames() {
        return names;
    }

    public String getTop() {
        return top;
    }
}
