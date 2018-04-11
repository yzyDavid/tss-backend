package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.RoleEntity;

import java.util.List;

public interface RoleRepository extends CrudRepository<RoleEntity, Short> {
    List<RoleEntity> findByName(String name);
}
