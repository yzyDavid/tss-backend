package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.CourseEntity;
import tss.entities.ProgramEntity;
import tss.entities.UserEntity;

import java.util.List;

/**
 * Author @zengzx
 */

public interface ProgramRepository extends CrudRepository<ProgramEntity, Short> {
    List<ProgramEntity> findByStudents(UserEntity student);
}
