package tss.responses.information.bbs;

import java.util.List;

public class CheckOutBoxResponse {
    private final String currentPage;
    private final String totalPage;
    private final List<String> destinations;
    private final List<String> titles;
    private final List<String> texts;
    private final List<String> times;
    private final List<String> userIDs;

    public CheckOutBoxResponse(String currentPage, String totalPage, List<String> destinations, List<String> titles, List<String> texts, List<String> times, List<String> userIDs) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.destinations = destinations;
        this.titles = titles;
        this.texts = texts;
        this.times = times;
        this.userIDs = userIDs;
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

    public List<String> getUserIDs() {
        return userIDs;
    }
}
