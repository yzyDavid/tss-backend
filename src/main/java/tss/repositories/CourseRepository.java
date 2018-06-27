package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends CrudRepository<CourseEntity, String> {
    List<CourseEntity> findByName(String name);

    List<CourseEntity> findByNameLike(String name);
}
