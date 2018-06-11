package tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tss.entities.ClassroomEntity;

/**
 * @author reeve
 */
public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Integer> {
}
