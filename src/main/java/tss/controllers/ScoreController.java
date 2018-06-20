package tss.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.entities.*;
import tss.repositories.*;
import tss.requests.information.*;
import tss.responses.information.*;

import java.util.*;

@Controller
@RequestMapping(path = "/grade")
public class ScoreController {

    private ClassRepository classRepository;
    private ModifyRepository modifyRepository;
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private ClassRegistrationRepository classRegistrationRepository;

    @Autowired
    ScoreController(ClassRepository classRepository, ModifyRepository modifyRepository,
                    UserRepository userRepository, CourseRepository courseRepository, ClassRegistrationRepository classRegistrationRepository) {
        this.classRepository = classRepository;
        this.modifyRepository = modifyRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.classRegistrationRepository = classRegistrationRepository;

    }

    @PostMapping(path = "/getallclass")
//    @Authorization
    public ResponseEntity<GetAllClassResponse> getallclass(@RequestBody GetAllClassRequest request) {
        Iterable<ClassEntity> classes = classRepository.findAll();
        List<String> courses_name = new ArrayList<>();
        List<Long> class_id = new ArrayList<>();
        for (ClassEntity classEntity : classes) {
            if (classEntity.getTeacher().getUid().equals(request.getUid()) && classEntity.getSemester().toString().equals(request.getSemester()) &&
                    classEntity.getYear().equals(request.getYear())) {
                courses_name.add(classEntity.getCourse().getName());
                class_id.add(classEntity.getId());
            }
        }
        return new ResponseEntity<>(new GetAllClassResponse("OK", courses_name, class_id), HttpStatus.OK);
    }

    @PostMapping(path = "/getclassstudent")
//    @Authorization
    public ResponseEntity<GetClassStudentResponse> getallclassstudents(@RequestBody GetClassStudentRequest request) {
        Optional<ClassEntity> ret = classRepository.findById(request.getCid());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetClassStudentResponse("no such class"), HttpStatus.BAD_REQUEST);
        }
        ClassEntity classEntity = ret.get();
        List<String> id = new ArrayList<>();
        List<String> name = new ArrayList<>();
        for (ClassRegistrationEntity classRegistrationEntity : classEntity.getClassRegistrations()) {
            id.add(classRegistrationEntity.getStudent().getUid());
            name.add(classRegistrationEntity.getStudent().getName());
        }
        return new ResponseEntity<>(new GetClassStudentResponse("OK", id, name), HttpStatus.OK);
    }

    @PostMapping(path = "/getclassstudentscore")//this is get student score in this semester's class;
//    @Authorization
    public ResponseEntity<GetClassStudentScoreResponse> getallclassstudentsscore(@RequestBody GetClassStudentScoreRequest request) {
        List<String> coursenames = new ArrayList<>();
        List<Long> classids = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        List<String> courseids = new ArrayList<>();
        Iterable<ClassRegistrationEntity> classRegistrationEntities = classRegistrationRepository.findAll();
        for (ClassRegistrationEntity classRegistrationEntity : classRegistrationEntities) {
            if (classRegistrationEntity.getStudent().getUid().equals(request.getUid()) &&
                    classRegistrationEntity.getClazz().getSemester().toString().equals(request.getSemester())
                    && classRegistrationEntity.getClazz().getYear().equals(request.getYear())) {
                coursenames.add(classRegistrationEntity.getClazz().getCourse().getName());
                courseids.add(classRegistrationEntity.getClazz().getCourse().getId());
                classids.add(classRegistrationEntity.getClazz().getId());
                scores.add(classRegistrationEntity.getScore());
            }
        }
        return new ResponseEntity<>(new GetClassStudentScoreResponse("ok", coursenames, courseids, scores, classids), HttpStatus.OK);
    }

    //this is return the grade of this class
    @PostMapping(path = "/getclassgrade")
    public ResponseEntity<GetClassGradeResponse> getgrade(@RequestBody GetClassGradeRequest request) {
        List<Integer> socres = new ArrayList<>();
        Optional<ClassEntity> ret = classRepository.findById(request.getCid());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetClassGradeResponse("no such class"), HttpStatus.BAD_REQUEST);
        }
        ClassEntity classEntity = ret.get();
        for (ClassRegistrationEntity classRegistrationEntity : classEntity.getClassRegistrations()) {
            socres.add(classRegistrationEntity.getScore());
        }
        return new ResponseEntity<>(new GetClassGradeResponse("ok", socres), HttpStatus.OK);
    }

    @PutMapping(path = "/add")
    public ResponseEntity<AddGradeResponse> addGrade(@RequestBody AddGradeRequest request) {
        Optional<ClassEntity> ret = classRepository.findById(request.getCid());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new AddGradeResponse("no such class"), HttpStatus.BAD_REQUEST);
        }
        ClassEntity classEntity = ret.get();
        List<String> studentids = request.getStudentid();
        List<String> id = request.getStudentid();
        List<Integer> scores = request.getScore();
        int count = 0;
        for (ClassRegistrationEntity classRegistrationEntity : classEntity.getClassRegistrations()) {
            for (int i = 0; i < studentids.size(); i++) {
                if (classRegistrationEntity.getStudent().getUid().equals(studentids.get(i))) {
                    classRegistrationEntity.setScore(request.getScore().get(i));
                    break;
                }
            }
        }
        classRepository.save(classEntity);
        return new ResponseEntity<>(new AddGradeResponse("ok"), HttpStatus.OK);
    }

    @PostMapping(path = "/modify")
    public ResponseEntity<ModifyGradeResponse> modifygrade(@RequestBody ModifyGradeRequest request) {
        Optional<ClassEntity> ret = classRepository.findById(request.getCid());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyGradeResponse("no such class"), HttpStatus.BAD_REQUEST);
        }
        ClassEntity classEntity = ret.get();
        for (ClassRegistrationEntity classRegistrationEntity : classEntity.getClassRegistrations()) {
            if (classRegistrationEntity.getStudent().getUid().equals(request.getStudentid())) {
                ModifyEntity modifyEntity = new ModifyEntity();
                modifyEntity.setCid(request.getCid());
                modifyEntity.setReasons(request.getReasons());
                modifyEntity.setScore(request.getScore());
                modifyEntity.setUid(request.getStudentid());
                modifyEntity.setAgree(false);
                modifyEntity.setProcess(false);
                modifyRepository.save(modifyEntity);
                return new ResponseEntity<>(new ModifyGradeResponse("modify action has been sumited"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ModifyGradeResponse("no such student in this class"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/getprocessmodify")
    public ResponseEntity<GetProcessResponse> getprocess(@RequestBody GetProcessRequest request) {

        Iterable<ModifyEntity> modifyEntities = modifyRepository.findAll();
        List<String> reasons = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        List<String> uids = new ArrayList<>();
        List<Long> cids = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (ModifyEntity modifyEntity : modifyEntities) {
            if (modifyEntity.getProcess() == false) {
                ids.add(modifyEntity.getId());
                reasons.add(modifyEntity.getReasons());
                scores.add(modifyEntity.getScore());
                uids.add(modifyEntity.getUid());
                cids.add(modifyEntity.getCid());
            }
        }
        return new ResponseEntity<>(new GetProcessResponse("ok", ids, reasons, scores, uids, cids), HttpStatus.OK);
    }

    @PostMapping(path = "/processmodify")
    public ResponseEntity<ProcessModifyResponse> processmodify(@RequestBody ProcessModifyRequest request) {
        if (request.getAgree().equals("true")) {
            Long cid = request.getCids();
            Optional<ClassEntity> ret = classRepository.findById(cid);
            if (!ret.isPresent()) {
                return new ResponseEntity<>(new ProcessModifyResponse("no such class"), HttpStatus.BAD_REQUEST);
            }
            ClassEntity classEntity = ret.get();
            for (ClassRegistrationEntity classRegistrationEntity : classEntity.getClassRegistrations()) {
                if (classRegistrationEntity.getStudent().getUid().equals(request.getUids())) {
                    classRegistrationEntity.setScore(request.getScore());
                }
            }
            classRepository.save(classEntity);
        }
        Optional<ModifyEntity> ret = modifyRepository.findById(request.getId());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ProcessModifyResponse("no such modify request"), HttpStatus.BAD_REQUEST);
        }
        ModifyEntity modifyEntity = ret.get();
        modifyEntity.setProcess(true);
        modifyEntity.setAgree(request.getAgree().equals("true"));
        modifyRepository.save(modifyEntity);
        return new ResponseEntity<>(new ProcessModifyResponse("ok"), HttpStatus.OK);

    }
//    @GetMapping(path = "/getstudentclass")
//    public ResponseEntity<GetStudentClassResponse>getstudentclass(@RequestBody GetStudentClassRequest request)
//    {
//        String uid = request.getUid();
//        Optional<UserEntity> ret = userRepository.findById(uid);
//        if(!ret.isPresent())
//        {
//            return new ResponseEntity<>(new GetStudentClassResponse("no such student"),HttpStatus.BAD_REQUEST);
//        }
//        UserEntity userEntity = ret.get();
//        Iterable<TakesEntity> takesEntities = takesRepository.findAll();
//        List<String> course = new ArrayList<>();
//        List<Long> class_id = new ArrayList<>();
//        for(TakesEntity takesEntity : takesEntities)
//        {
//            if(takesEntity.getStudent().getUid().equals(uid))
//            {
//                course.add(takesEntity.getClassEntity().getCourse().getName());
//                class_id.add(takesEntity.getClassEntity().getId());
//            }
//        }
//        return new ResponseEntity<>(new GetStudentClassResponse("ok",course,class_id),HttpStatus.OK);
//    }
}
