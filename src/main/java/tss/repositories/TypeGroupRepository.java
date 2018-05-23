package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.TypeGroupEntity;

import java.util.Optional;

public interface TypeGroupRepository extends CrudRepository<TypeGroupEntity, Short> {
    Optional<TypeGroupEntity> findByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);
}
