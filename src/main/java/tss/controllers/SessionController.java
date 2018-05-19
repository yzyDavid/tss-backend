package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.entities.SessionEntity;
import tss.entities.UserEntity;
import tss.repositories.SqlSessionRepository;
import tss.repositories.UserRepository;
import tss.requests.session.LoginRequest;
import tss.responses.session.LoginResponse;
import tss.utils.SecurityUtils;
import tss.utils.SessionUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static tss.utils.SecurityUtils.getHashedPasswordByPasswordAndSalt;
import static tss.utils.SecurityUtils.getSalt;

/**
 * @author yzy
 * <p>
 * RESTful APIs for login(creating), log out tokens, etc.
 */
@Controller
@RequestMapping(path = "/session")
public class SessionController {
    private final UserRepository userRepository;

    private final SqlSessionRepository sqlSessionRepository;

    @Autowired
    public SessionController(UserRepository userRepository, SqlSessionRepository sqlSessionRepository) {
        this.userRepository = userRepository;
        this.sqlSessionRepository = sqlSessionRepository;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) {

        String uid = login.getUid();

        // TODO FIXME: why creating new user entity here?
        UserEntity entity = new UserEntity();
        entity.setUid(uid);
        System.out.println(uid);
        entity.setName("wen");
        String salt = getSalt();
        String hashedPassword = getHashedPasswordByPasswordAndSalt("123456", salt);
        entity.setSalt(salt);
        entity.setHashedPassword(hashedPassword);

        userRepository.save(entity);

        if (!userRepository.existsById(login.getUid())) {
            return new ResponseEntity<>(new LoginResponse("", "", null, "User not exists"), HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userRepository.findById(login.getUid()).get();
        if (!user.getHashedPassword().equals(SecurityUtils.getHashedPasswordByPasswordAndSalt(login.getPassword(), user.getSalt()))) {
            return new ResponseEntity<>(new LoginResponse("", "", null, "Password incorrect"), HttpStatus.BAD_REQUEST);
        }

        SessionEntity session = new SessionEntity();
        session.setUid(login.getUid());
        session.setToken(SessionUtils.getToken());
        session.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        if (sqlSessionRepository.existsByUid(login.getUid())) {
            // TODO: process with duplicated insert.
            SessionEntity sessionToRemove = sqlSessionRepository.findByUid(login.getUid());
            sqlSessionRepository.delete(sessionToRemove);
        }

        sqlSessionRepository.save(session);

        return new ResponseEntity<>(new LoginResponse(login.getUid(), session.getToken(), user.readTypeName(), "OK"), HttpStatus.OK);
    }
}
