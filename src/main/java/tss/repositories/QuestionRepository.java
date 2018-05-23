package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.QuestionEntity;

import java.util.List;

public interface QuestionRepository extends CrudRepository<QuestionEntity, String> {
    List<QuestionEntity> findByQid(String qid);

    List<QuestionEntity> findByQtype(String qtype);

    List<QuestionEntity> findByQunit(String qunit);
}
