package tss.repositories;


import org.springframework.data.repository.CrudRepository;
import tss.entities.HistoryGradeEntity;

import java.util.List;

public interface HistoryGradeRepository extends CrudRepository<HistoryGradeEntity, String> {
    List<HistoryGradeEntity> findBySid(String Sid);

    List<HistoryGradeEntity> findByPid(String Pid);
}
