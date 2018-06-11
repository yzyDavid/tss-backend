package tss.repositories.bbs;

import org.springframework.data.repository.CrudRepository;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsRetrieveEntity;

import java.util.List;

public interface BbsRetrieveRepository extends CrudRepository<BbsRetrieveEntity, Long>{
    List<BbsRetrieveEntity> findByReceiver(UserEntity receiver);
    List<BbsRetrieveEntity> findBySender(UserEntity sender);
}
