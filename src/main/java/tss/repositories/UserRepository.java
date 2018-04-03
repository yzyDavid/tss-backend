package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.UserEntity;

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
