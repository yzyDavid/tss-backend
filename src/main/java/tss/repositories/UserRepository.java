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
     * @return Optional<UserEntity>
     * description
     */
    List<UserEntity> findByName(String name);
}
