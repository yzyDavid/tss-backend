package tss.session;

import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface SessionRepository extends CrudRepository<SessionEntity, Long> {
}
