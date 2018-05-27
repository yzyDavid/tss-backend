package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.CurrentYearSemesterOfArrangementEntity;

import java.util.List;

/**
 * @author reeve
 */
public interface CurrentYearSemesterOfArrangementRepository
        extends CrudRepository<CurrentYearSemesterOfArrangementEntity, Integer> {

    List<CurrentYearSemesterOfArrangementEntity> findByValid(boolean valid);
}
