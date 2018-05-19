package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.CourseEntity;
import tss.entities.ProgramCourseEntity;
import tss.entities.ProgramEntity;
import tss.entities.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * @Author zengzx
 */

public interface ProgramCourseRepository extends CrudRepository<ProgramCourseEntity, Short> {
    boolean existsByCourse(CourseEntity course);
    boolean existsByProgram(ProgramEntity program);
    List<CourseEntity> findById(Integer pid);
    Optional<ProgramCourseEntity> findByCourseAndProgram(CourseEntity course, ProgramEntity program);

}
