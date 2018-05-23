package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.UserEntity;

import java.util.List;

/**
 * @author yzy
 */
public interface UserRepository extends CrudRepository<UserEntity, String> {
    /**
     * @param name
     * @return boolean
     * check exists of a UID
     */
    List<UserEntity> findByName(String name);
}
