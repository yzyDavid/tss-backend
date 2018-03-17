package tss.session;

import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface SqlSessionRepository extends CrudRepository<SessionEntity, Long> {
    boolean existsByUid(String uid);

    SessionEntity findByUid(String uid);
}
