package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.TimeSlotEntity;

public interface TimeSlotRepository extends CrudRepository<TimeSlotEntity, Short> {
}
