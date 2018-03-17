package tss.session;

import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface SqlSessionRepository extends CrudRepository<SessionEntity, Long> {
}
