package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.CourseEntity;
import tss.entities.ProgramCourseEntity;
import tss.entities.UserEntity;

import java.util.List;

/**
 * @Author zengzx
 */

public interface ProgramCourseRepository extends CrudRepository<ProgramCourseEntity, Short> {
    boolean existsById(Integer pid);



    List<CourseEntity> findById(Integer pid);

}
