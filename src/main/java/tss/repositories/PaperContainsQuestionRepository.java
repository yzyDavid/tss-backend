package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.PaperContainsQuestionEntity;
import tss.entities.PapersEntity;

import java.util.List;

public interface PaperContainsQuestionRepository extends CrudRepository<PaperContainsQuestionEntity, String> {
    List<PaperContainsQuestionEntity> findByPaper(PapersEntity paper);
    void deleteByPaper(PapersEntity paper);
}
