package tss.repositories.bbs;

import org.springframework.data.repository.CrudRepository;
import tss.entities.bbs.BbsTakeEntity;

import java.util.Optional;
import java.util.Set;

public interface BbsTakeRepository extends CrudRepository<BbsTakeEntity, String>{
    Set<BbsTakeEntity> findBySid(Long sid);
    Set<BbsTakeEntity> findByUid(String uid);
    Optional<BbsTakeEntity> findByUidAndSid(String uid, Long sid);
}
