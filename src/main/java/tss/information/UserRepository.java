package tss.information;

import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface UserRepository extends CrudRepository<UserModel, Long> {
}
