package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.PapersEntity;

public interface PaperRepository extends CrudRepository<PapersEntity, String> {

}
