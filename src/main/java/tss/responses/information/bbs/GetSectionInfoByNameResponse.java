package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;
import tss.entities.bbs.BbsTopicEntity;

import java.util.Set;

public class GetSectionInfoByNameResponse {
    @Nls
    private final String status;
    private final long id;
    private final String name;
    private final int userNum;
    private final Set<String> topics;

    public GetSectionInfoByNameResponse(String status, long id, String name, int userNum, Set<String> topics) {
        this.status = status;
        this.id = id;
        this.name = name;
        this.userNum = userNum;
        this.topics = topics;
    }

    public String getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUserNum() {
        return userNum;
    }

    public Set<String> getTopics() {
        return topics;
    }
}
