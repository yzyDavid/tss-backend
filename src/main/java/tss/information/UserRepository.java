package tss.information;

import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface UserRepository extends CrudRepository<UserEntity, String> {
    /**
     * @param uid
     * @return boolean
     * check exists of a UID
     */

}
