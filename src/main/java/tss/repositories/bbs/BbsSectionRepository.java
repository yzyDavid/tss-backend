package tss.repositories.bbs;

import org.springframework.data.repository.CrudRepository;
import tss.entities.bbs.BbsSectionEntity;

import java.util.Optional;

public interface BbsSectionRepository extends CrudRepository<BbsSectionEntity, Long> {
    boolean existsByName(String name);

    Optional<BbsSectionEntity> findByName(String name);
}
