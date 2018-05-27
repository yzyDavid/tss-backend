package tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tss.entities.ClassEntity;
import tss.entities.CourseEntity;

import java.util.List;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
}
