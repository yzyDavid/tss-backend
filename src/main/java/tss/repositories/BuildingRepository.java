package tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tss.entities.BuildingEntity;

/**
 * @author reeve
 */
public interface BuildingRepository extends JpaRepository<BuildingEntity, Integer> {
}
