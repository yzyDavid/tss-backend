package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.SelectionTimeEntity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * @author ljh-A3
 */
public interface SelectionTimeRepository extends CrudRepository<SelectionTimeEntity, Integer> {
    Optional<SelectionTimeEntity> findByStartAndEnd(Timestamp start, Timestamp end);
}