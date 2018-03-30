package tss.information;

import org.springframework.data.repository.CrudRepository;

public interface InstructorRepository extends CrudRepository<UserEntity, Long> {
}
