package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.TeachesEntity;

public interface TeachesRepository extends CrudRepository<TeachesEntity, Long> {
}
