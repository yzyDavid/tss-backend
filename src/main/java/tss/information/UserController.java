package tss.information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author yzy
 */
@Controller
@RequestMapping(path = "/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping(path = "/add")
    public ResponseEntity<AddUserMessage> add(@RequestBody UserEntity user) {
        String uid = user.getUid();
        if (userRepository.existsByUid(uid)) {
            return new ResponseEntity<>(new AddUserMessage("failed with duplicated uid", "", ""), HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity<>(new AddUserMessage("OK", uid, user.getName()), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginMessage login) {
        // TODO: check valid

        return new ResponseEntity<>(new Object(), HttpStatus.OK);
    }
}
