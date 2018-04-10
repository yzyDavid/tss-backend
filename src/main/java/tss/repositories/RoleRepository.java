package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Short> {

}
