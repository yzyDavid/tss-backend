package tss.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.information.UserRepository;

/**
 * @author yzy
 * <p>
 * RESTful APIs for login(creating), log out tokens, etc.
 */
@Controller
@RequestMapping(path = "/session")
public class SessionController {
    private final UserRepository userRepository;

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionController(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<SetTokenMessage> login(@RequestBody LoginMessage login) {
        // TODO: check valid

        return new ResponseEntity<>(new SetTokenMessage("", ""), HttpStatus.OK);
    }
}
