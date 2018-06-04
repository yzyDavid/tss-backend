package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.SessionEntity;

import java.util.Optional;

/**
 * @author yzy
 */
public interface SqlSessionRepository extends CrudRepository<SessionEntity, Long> {
    boolean existsByUid(String uid);

    Optional<SessionEntity> findByUid(String uid);

    boolean existsByToken(String token);

    Optional<SessionEntity> findByToken(String token);
}
