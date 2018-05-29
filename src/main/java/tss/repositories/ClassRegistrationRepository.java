package tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tss.entities.ClassRegistrationEntity;
import tss.entities.ClassRegistrationId;

import java.util.List;
import java.util.Optional;

/**
 * @author reeve
 */
public interface ClassRegistrationRepository extends JpaRepository<ClassRegistrationEntity, ClassRegistrationId> {
    public Optional<ClassRegistrationEntity> findById(ClassRegistrationId id);
}
