package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.DepartmentEntity;

import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Short> {
    boolean existsByName(String name);

    Optional<DepartmentEntity> findByName(String name);

    void deleteByName(String name);
}
