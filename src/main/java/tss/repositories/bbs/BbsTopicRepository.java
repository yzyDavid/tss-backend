package tss.repositories.bbs;

import org.springframework.data.repository.CrudRepository;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsTopicEntity;

import java.util.Optional;

public interface BbsTopicRepository extends CrudRepository<BbsTopicEntity, Long>{
    boolean existsByName(String name);
    Optional<BbsTopicEntity> findByName(String name);
    Optional<BbsTopicEntity> findByAuthor(UserEntity author);
}
