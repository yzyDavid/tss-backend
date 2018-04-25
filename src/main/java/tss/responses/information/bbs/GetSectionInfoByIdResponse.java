package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;
import tss.entities.bbs.BbsSectionEntity;
import tss.entities.bbs.BbsTopicEntity;

import java.util.Set;

public class GetSectionInfoByIdResponse {
    @Nls
    private final String status;
    private final long id;
    private final String name;
    private final int userNum;
    private final Set<BbsTopicEntity> topics;

    public GetSectionInfoByIdResponse(String status, long id, String name, int userNum, Set<BbsTopicEntity> topics){
        this.status = status;
        this.id = id;
        this.name = name;
        this.userNum = userNum;
        this.topics = topics;
    }

    public String getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUserNum() {
        return userNum;
    }

    public Set<BbsTopicEntity> getTopics() {
        return topics;
    }
}

