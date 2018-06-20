package tss.repositories.bbs;

import org.springframework.data.repository.CrudRepository;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsTopicEntity;

import java.util.List;
import java.util.Optional;

public interface BbsTopicRepository extends CrudRepository<BbsTopicEntity, Long> {
    boolean existsByName(String name);

    Optional<List<BbsTopicEntity>> findByName(String name);

    Optional<List<BbsTopicEntity>> findByAuthor(UserEntity author);
}
