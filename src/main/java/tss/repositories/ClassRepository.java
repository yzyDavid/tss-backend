package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.ClassEntity;

public interface ClassRepository extends CrudRepository<ClassEntity, Long> {
}
