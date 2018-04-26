package tss.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.ClassEntity;
import tss.entities.UserEntity;
import tss.exceptions.ClazzNotFoundException;
import tss.exceptions.PermissionDeniedException;
import tss.exceptions.TeacherNotFoundException;
import tss.repositories.ClassRepository;
import tss.repositories.UserRepository;

/**
 * @author reeve
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    public TeacherController(UserRepository userRepository, ClassRepository classRepository) {
        this.userRepository = userRepository;
        this.classRepository = classRepository;
    }

    @PutMapping("/{userId}/classes-teaching/{classId}")
    @ResponseStatus(value = HttpStatus.OK)
    @Authorization
    public void addClassTeaching(@CurrentUser UserEntity user,
                                 @PathVariable String userId, @PathVariable long classId) {
        if (user.getType() != UserEntity.TYPE_MANAGER) {
            throw new PermissionDeniedException();
        }

        UserEntity teacherEntity = userRepository.findById(userId).orElseThrow(TeacherNotFoundException::new);
        if (teacherEntity.getType() != UserEntity.TYPE_TEACHER) {
            throw new TeacherNotFoundException();
        }
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(ClazzNotFoundException::new);

        teacherEntity.addClassTeaching(classEntity);
        userRepository.save(teacherEntity);
    }
}
