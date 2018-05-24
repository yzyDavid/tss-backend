package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.MajorClassEntity;

import java.util.Optional;

public interface MajorClassRepository extends CrudRepository<MajorClassEntity, Short> {
    boolean existsByName(String name);

    Optional<MajorClassEntity> findByName(String name);

    void deleteByName(String name);
}
