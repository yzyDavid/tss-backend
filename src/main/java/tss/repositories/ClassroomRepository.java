package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.ClassroomEntity;

public interface ClassroomRepository extends CrudRepository<ClassroomEntity, Short> {
}
