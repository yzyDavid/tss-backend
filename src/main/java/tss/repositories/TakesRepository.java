package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.TakesEntity;

public interface TakesRepository extends CrudRepository<TakesEntity, Long> {
}
