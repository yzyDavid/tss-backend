package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.DepartmentEntity;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Short> {
}
