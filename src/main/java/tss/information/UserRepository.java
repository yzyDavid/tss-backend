package tss.information;

import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    /**
     * @param uid
     * @return boolean
     */
    boolean existsByUid(String uid);
}
