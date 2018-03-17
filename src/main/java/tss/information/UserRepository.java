package tss.information;

import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    /**
     * @param uid
     * @return boolean
     * check exists of a UID
     */
    boolean existsByUid(String uid);

    UserEntity findByUid(String uid);
}
