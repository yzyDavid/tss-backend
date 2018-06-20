package tss.repositories.bbs;

import org.springframework.data.repository.CrudRepository;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsReplyEntity;
import tss.entities.bbs.BbsTopicEntity;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface BbsReplyRepository extends CrudRepository<BbsReplyEntity, Long> {
    BbsReplyEntity findByBelongedTopicAndIndex(BbsTopicEntity topicEntity, Integer index);

    List<BbsReplyEntity> findByBelongedTopic(BbsTopicEntity topicEntity);

    Optional<List<BbsReplyEntity>> findByAuthor(UserEntity userEntity);
}
