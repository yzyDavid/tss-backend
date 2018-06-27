package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.UserEntity;

import java.util.List;
import java.util.Optional;

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

    Optional<UserEntity> findByUid(String uid);

    List<UserEntity> findByNameAndType_Name(String name, String typeName);

    List<UserEntity> findByNameLikeAndType_Name(String name, String typeName);

}
