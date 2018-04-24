package tss.repositories.bbs;

import org.springframework.data.repository.CrudRepository;
import tss.entities.bbs.BbsSectionEntity;

public interface BbsSectionRepository extends CrudRepository<BbsSectionEntity, Long>{
    boolean existsByName(String name);
}
