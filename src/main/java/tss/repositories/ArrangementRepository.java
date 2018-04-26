package tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tss.entities.ArrangementEntity;
import tss.entities.ArrangementId;

/**
 * @author reeve
 */
public interface ArrangementRepository extends JpaRepository<ArrangementEntity, ArrangementId> {
}
