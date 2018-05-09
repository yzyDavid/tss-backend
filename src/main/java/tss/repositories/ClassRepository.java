package tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tss.entities.ClassEntity;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
}
