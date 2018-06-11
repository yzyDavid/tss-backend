package tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tss.entities.ClassRegistrationEntity;
import tss.entities.ClassRegistrationId;

/**
 * @author reeve
 */
public interface ClassRegistrationRepository extends JpaRepository<ClassRegistrationEntity, ClassRegistrationId> {
}
