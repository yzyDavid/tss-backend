package tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tss.entities.TimeSlotEntity;

/**
 * @author reeve
 */
public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {
}
