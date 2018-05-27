package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.RoleEntity;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Short> {
    Optional<RoleEntity> findByName(String name);
}
