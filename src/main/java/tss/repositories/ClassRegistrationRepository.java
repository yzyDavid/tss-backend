package tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tss.entities.*;

import java.util.List;
import java.util.Optional;

/**
 * @author reeve
 */
public interface ClassRegistrationRepository extends JpaRepository<ClassRegistrationEntity, ClassRegistrationId> {
    public Optional<ClassRegistrationEntity> findByCrid(String crid);
    public List<ClassRegistrationEntity> findByStudent(UserEntity student);
    public List<ClassRegistrationEntity> findByStudentAndClazz(UserEntity student, ClassEntity clazz);
    public Optional<ClassRegistrationEntity> findByStudentAndClazz_Course(UserEntity student, CourseEntity course);
}
