package tss.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.CourseEntity;
import tss.entities.TeachesEntity;
import tss.entities.UserEntity;
import tss.repositories.CourseRepository;
import tss.repositories.TeachesRepository;
import tss.repositories.UserRepository;
import tss.requests.information.AddTeachesRequest;
import tss.responses.information.AddTeachesResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/teacher")
public class TeacherController {
    private final TeachesRepository teachesRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public TeacherController(TeachesRepository teachesRepository, UserRepository userRepository,
                             CourseRepository courseRepository) {
        this.teachesRepository = teachesRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @PutMapping(path = "/instructor")
    @Authorization
    public ResponseEntity<AddTeachesResponse> addTeaches(@CurrentUser UserEntity user,
                                                         @RequestBody AddTeachesRequest request) {
        String cid = request.getCid();
        List<String> uids = request.getUids();
        Optional<CourseEntity> ret = courseRepository.findById(cid);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new AddTeachesResponse("course doesn't exist", null), HttpStatus.BAD_REQUEST);
        }
        CourseEntity course = ret.get();
        List<TeachesEntity> teaches = new ArrayList<>();
        for (String uid : uids) {
            Optional<UserEntity> retu = userRepository.findById(uid);
            if (!retu.isPresent()) {
                return new ResponseEntity<>(new AddTeachesResponse("uid does't exist", uid), HttpStatus.BAD_REQUEST);
            }
            UserEntity teacher = retu.get();
            if (!teacher.readTypeName().equals("Teacher")) {
                return new ResponseEntity<>(new AddTeachesResponse("user is not a teacher", uid), HttpStatus.BAD_REQUEST);
            }
            //TODO: check duplication
            teaches.add(new TeachesEntity(teacher, course));
        }
        for (TeachesEntity item : teaches) {
            teachesRepository.save(item);
        }
        return new ResponseEntity<>(new AddTeachesResponse("OK", null), HttpStatus.OK);
    }


}
