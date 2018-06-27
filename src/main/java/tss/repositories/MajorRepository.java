package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.MajorEntity;

import java.util.Optional;

public interface MajorRepository extends CrudRepository<MajorEntity, Short> {
    boolean existsByName(String name);

    Optional<MajorEntity> findByName(String name);
}
