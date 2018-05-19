package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.ClassEntity;
import tss.entities.CourseEntity;

import java.util.List;

public interface ClassRepository extends CrudRepository<ClassEntity, Long> {
    List<ClassEntity> findById(Integer id);
    List<ClassEntity> findByCourse(CourseEntity course);
}
