package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.SessionEntity;

/**
 * @author yzy
 */
public interface SqlSessionRepository extends CrudRepository<SessionEntity, Long> {
    boolean existsByUid(String uid);

    SessionEntity findByUid(String uid);

    boolean existsByToken(String token);

    SessionEntity findByToken(String token);
}
