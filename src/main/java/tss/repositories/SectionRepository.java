package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.SectionEntity;

public interface SectionRepository extends CrudRepository<SectionEntity, Long> {
}
