package tss.repositories;


import org.springframework.data.repository.CrudRepository;
import tss.entities.HistoryGradeEntity;
import java.util.List;

public interface HistoryGradeRepository extends CrudRepository<HistoryGradeEntity, String> {
    List<HistoryGradeEntity> findByStudent(String Sid);
    List<HistoryGradeEntity> findBypaper(String Pid);
}
