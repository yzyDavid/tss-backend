package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.ResultEntity;
import java.util.List;

public interface ResultRepository extends CrudRepository<ResultEntity, String> {
    List<ResultEntity> findByStudent(String Sid);
    List<ResultEntity> findByQuestion(String Qid);
    List<ResultEntity> findByPaper(String Pid);
}
