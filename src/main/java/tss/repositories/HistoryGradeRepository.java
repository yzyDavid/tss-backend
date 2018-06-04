package tss.repositories;


import org.springframework.data.repository.CrudRepository;
import tss.entities.HistoryGradeEntity;
import tss.entities.PapersEntity;
import tss.entities.UserEntity;

import java.util.List;

public interface HistoryGradeRepository extends CrudRepository<HistoryGradeEntity, String> {

    List<HistoryGradeEntity> findByStudent(UserEntity student);

    //List<HistoryGradeEntity> findByPaper(PapersEntity paper);

}
