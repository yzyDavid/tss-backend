package tss.information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static tss.utils.SecurityUtils.getHashedPasswordByPasswordAndSalt;
import static tss.utils.SecurityUtils.getSalt;

/**
 * @author yzy
 * <p>
 * For management of User register, delete and query, etc.
 * NOT for sessions.
 */
@Controller
@RequestMapping(path = "/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping(path = "")
    public ResponseEntity<AddUserResponse> add(@RequestBody AddUserRequest user) {
        String uid = user.getUid();
        if (userRepository.existsByUid(uid)) {
            return new ResponseEntity<>(new AddUserResponse("failed with duplicated uid", "", ""), HttpStatus.BAD_REQUEST);
        }

        UserEntity entity = new UserEntity();
        entity.setUid(uid);
        entity.setName(user.getName());
        String salt = getSalt();
        String hashedPassword = getHashedPasswordByPasswordAndSalt(user.getPassword(), salt);
        entity.setSalt(salt);
        entity.setHashedPassword(hashedPassword);

        userRepository.save(entity);
        return new ResponseEntity<>(new AddUserResponse("OK", uid, user.getName()), HttpStatus.CREATED);
    }
}
