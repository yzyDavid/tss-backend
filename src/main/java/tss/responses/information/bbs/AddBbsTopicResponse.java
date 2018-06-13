package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.Date;

public class AddBbsTopicResponse {
    private final String topicID;

    public AddBbsTopicResponse(String topicID) {
        this.topicID = topicID;
    }

    public String getTopicID() {
        return topicID;
    }
}
