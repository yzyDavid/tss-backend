package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.CourseEntity;

import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, String> {
    List<CourseEntity> findByName(String name);
}
