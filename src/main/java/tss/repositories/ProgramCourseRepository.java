package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.CourseEntity;
import tss.entities.ProgramCourseEntity;
import tss.entities.UserEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * @Author zengzx
 */

public interface ProgramCourseRepository extends CrudRepository<ProgramCourseEntity, Short> {
    boolean existsByCourse(CourseEntity course);
    boolean existsByStudent(UserEntity student);
    boolean existsByCourseAndStudent(CourseEntity course, UserEntity student);
    List<CourseEntity> findById(Integer pid);
    Optional<ProgramCourseEntity> findByCourseAndStudent(CourseEntity course, UserEntity student);

}
