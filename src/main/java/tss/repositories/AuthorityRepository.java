package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.AuthorityEntity;

public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Short> {
}
