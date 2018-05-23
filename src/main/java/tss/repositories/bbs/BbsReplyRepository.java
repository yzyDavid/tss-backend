package tss.repositories.bbs;

import org.springframework.data.repository.CrudRepository;
import tss.entities.bbs.BbsReplyEntity;
import tss.entities.bbs.BbsTopicEntity;

import java.util.List;

public interface BbsReplyRepository extends CrudRepository<BbsReplyEntity, Long>{
    BbsReplyEntity findByBelongedTopicAndIndex(BbsTopicEntity topicEntity, Integer index);
    List<BbsReplyEntity> findByBelongedTopic(BbsTopicEntity topicEntity);

}
