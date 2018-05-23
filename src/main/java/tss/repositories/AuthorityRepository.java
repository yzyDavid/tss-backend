package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.AuthorityEntity;

import java.util.Optional;

public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Short> {
    Optional<AuthorityEntity> findByUri(String Uri);
}
