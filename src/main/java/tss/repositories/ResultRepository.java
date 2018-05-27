package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.PapersEntity;
import tss.entities.QuestionEntity;
import tss.entities.ResultEntity;
import tss.entities.UserEntity;

import java.util.List;

public interface ResultRepository extends CrudRepository<ResultEntity, String> {

    List<ResultEntity> findByStudent(UserEntity student);

    List<ResultEntity> findByQuestion(QuestionEntity question);

    List<ResultEntity> findByPaper(PapersEntity paper);

}
