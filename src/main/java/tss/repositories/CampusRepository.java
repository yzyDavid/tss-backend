package tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tss.entities.CampusEntity;

/**
 * @author reeve
 */
public interface CampusRepository extends JpaRepository<CampusEntity, Integer> {
}
