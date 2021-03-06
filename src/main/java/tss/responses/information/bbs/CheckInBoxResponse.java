package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.Date;
import java.util.List;

public class CheckInBoxResponse {
    private final String currentPage;
    private final String totalPage;
    private final List<String> destinations;
    private final List<String> titles;
    private final List<String> texts;
    private final List<String> times;
    private final List<String> reads;
    private final List<String> userIDs;
    private final List<String> letterIDs;

    public CheckInBoxResponse(String currentPage, String totalPage, List<String> destinations, List<String> titles, List<String> texts, List<String> times, List<String> reads, List<String> userIDs, List<String> letterIDs) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.destinations = destinations;
        this.titles = titles;
        this.texts = texts;
        this.times = times;
        this.reads = reads;
        this.userIDs = userIDs;
        this.letterIDs = letterIDs;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getTexts() {
        return texts;
    }

    public List<String> getTimes() {
        return times;
    }

    public List<String> getReads() {
        return reads;
    }

    public List<String> getUserIDs() {
        return userIDs;
    }

    public List<String> getLetterIDs() {
        return letterIDs;
    }
}
