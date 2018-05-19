package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.ResultEntity;

import java.util.List;

public interface ResultRepository extends CrudRepository<ResultEntity, String> {
    List<ResultEntity> findBySid(String Sid);

    List<ResultEntity> findByQid(String Qid);

    List<ResultEntity> findByPid(String Pid);
}
