package tss.repositories;

import org.springframework.data.repository.CrudRepository;
import tss.entities.CourseEntity;

public interface CourseRepository extends CrudRepository<CourseEntity, String> {

}
