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
public class GradeController {

    private ClassRepository classRepository;
    private ModifyRepository modifyRepository;
    private UserRepository userRepository;
    private TeachesRepository teachesRepository;
    private TakesRepository takesRepository;
    private CourseRepository courseRepository;

    @Autowired
    GradeController(ClassRepository classRepository, ModifyRepository modifyRepository, TeachesRepository teachesRepository,
                    UserRepository userRepository, TakesRepository takesRepository,CourseRepository courseRepository)
    {
        this.classRepository=classRepository;
        this.modifyRepository=modifyRepository;
        this.teachesRepository=teachesRepository;
        this.userRepository=userRepository;
        this.takesRepository=takesRepository;
        this.courseRepository=courseRepository;

    }
    @GetMapping(path = "/getall")
//    @Authorization
    public ResponseEntity<AddClassResponse> getClass(
//            @CurrentUser UserEntity user,
            @RequestBody AddClassRequest request) {
//        String cid = request.getCid();
//        Optional<UserEntity> ret = userRepository.findById("123456");
//        UserEntity userEntity=ret.get();
//        Optional<CourseEntity> ret_course= courseRepository.findById("0");
//        CourseEntity courseEntity =ret_course.get();
//        TeachesEntity teachesEntity = new TeachesEntity(userEntity,courseEntity);
//        Iterable<ClassEntity> classEntities = classRepository.findAll();
//        Set<ClassEntity> set = new HashSet<>();
//        for(ClassEntity classEntity:classEntities)
//            set.add(classEntity);
//        teachesEntity.setClasses(set);
//        teachesRepository.save(teachesEntity);
//        Iterable<TeachesEntity> teachesEntities = teachesRepository.findAll();
        Long id =24L;
        Optional<ClassEntity> ret_class = classRepository.findById(id);
        ClassEntity classEntity = ret_class.get();
        Set<TakesEntity> takesEntities = new HashSet<>();
        for(int i=0;i<5;i++)
        {
            Optional<UserEntity> ret= userRepository.findById((i+1)+"");
            UserEntity userEntity = ret.get();
            Optional<CourseEntity> ret_course= courseRepository.findById("0");
            CourseEntity courseEntity =ret_course.get();
            TakesEntity takesEntity = new TakesEntity(userEntity,0,2018,'S',"0",classEntity);
            takesEntities.add(takesEntity);
            takesRepository.save(takesEntity);
        }
        classEntity.setTakes(takesEntities);
        classRepository.save(classEntity);
        return new ResponseEntity<>(new AddClassResponse("OK"+takesRepository.findAll().toString()), HttpStatus.OK);
    }
    @PostMapping(path = "/getallclass")
//    @Authorization
    public ResponseEntity<GetAllClassResponse> getallclass(@RequestBody GetAllClassRequest request)
    {
        String uid = request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent())
        {
            return new ResponseEntity<>(new GetAllClassResponse("teacher not exist"),HttpStatus.BAD_REQUEST);
        }
        UserEntity teacher = ret.get();
        Iterable<TeachesEntity> teaches= teachesRepository.findAll();
        List<String> courses_name = new ArrayList<>();
        List<Long> class_id = new ArrayList<>();
        for(TeachesEntity teachesEntity:teaches)
        {
            if(teachesEntity.getTeacher().getUid().equals(request.getUid()))
            {
                if(teachesEntity.getCourse().getSemester().equals(request.getSemester()))
                {
                    for(ClassEntity classEntity : teachesEntity.getClasses())
                    {
                        courses_name.add(classEntity.getCourse().getName());
                        class_id.add(classEntity.getId());
                    }
                }
            }
        }
        return new ResponseEntity<>(new GetAllClassResponse("OK",courses_name,class_id),HttpStatus.OK);
    }
    @PostMapping(path = "/getclassstudent")
//    @Authorization
    public ResponseEntity<GetClassStudentResponse> getallclassstudents(@RequestBody GetClassStudentRequest request)
    {
        String uid = request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent())
        {
            return new ResponseEntity<>(new GetClassStudentResponse("teacher not exist"),HttpStatus.BAD_REQUEST);
        }
        UserEntity teacher = ret.get();
        Iterable<TeachesEntity> teaches= teachesRepository.findAll();
        Optional<ClassEntity> retclass=classRepository.findById(request.getCid());
        if(!retclass.isPresent())
        {
            return new ResponseEntity<>(new GetClassStudentResponse("class not exist"),HttpStatus.OK);
        }
        ClassEntity classEntity =retclass.get();
        List<String> id = new ArrayList<>();
        List<String> name = new ArrayList<>();
        for(TakesEntity takesEntity:classEntity.getTakes())
        {
            id.add(takesEntity.getStudent().getUid());
            name.add(takesEntity.getStudent().getName());
        }
        return new ResponseEntity<>(new GetClassStudentResponse("OK",id,name),HttpStatus.OK);
    }

    @GetMapping(path = "/getclassstudentscore")
//    @Authorization
    public ResponseEntity<GetClassStudentScoreResponse> getallclassstudentsscore(@RequestBody GetClassStudentScoreRequest request)
    {
        String uid = request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent())
        {
            return new ResponseEntity<>(new GetClassStudentScoreResponse("teacher not exist"),HttpStatus.BAD_REQUEST);
        }
        UserEntity teacher = ret.get();
        Iterable<TeachesEntity> teaches= teachesRepository.findAll();
        Optional<ClassEntity> retclass=classRepository.findById(request.getCid());
        if(!retclass.isPresent())
        {
            return new ResponseEntity<>(new GetClassStudentScoreResponse("class not exist"),HttpStatus.BAD_REQUEST);
        }
        ClassEntity classEntity =retclass.get();
        List<String> id = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<Integer> score = new ArrayList<>();
        for(TakesEntity takesEntity:classEntity.getTakes())
        {
            id.add(takesEntity.getStudent().getUid());
            name.add(takesEntity.getStudent().getName());
            score.add(takesEntity.getScore());
        }
        return new ResponseEntity<>(new GetClassStudentScoreResponse("OK",id,name,score),HttpStatus.OK);
    }
    @PutMapping(path="/add")
    public ResponseEntity<AddGradeResponse> addGrade(@RequestBody AddGradeRequest request)
    {
        String uid = request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent())
        {
            return new ResponseEntity<>(new AddGradeResponse("teacher not exist"),HttpStatus.BAD_REQUEST);
        }
        UserEntity teacher = ret.get();
        Iterable<TeachesEntity> teaches= teachesRepository.findAll();
        Optional<ClassEntity> retclass=classRepository.findById(request.getCid());
        if(!retclass.isPresent())
        {
            return new ResponseEntity<>(new AddGradeResponse("class not exist"),HttpStatus.BAD_REQUEST);
        }
        ClassEntity classEntity =retclass.get();
        List<String> id = request.getStudentid();
        List<Integer> scores=request.getScore();
        int count=0;
        for(TakesEntity takesEntity:classEntity.getTakes())
        {
            if(takesEntity.getStudent().getUid().equals(id.get(count)))
            {
                takesEntity.setScore(scores.get(count++));
            }
        }
        classRepository.save(classEntity);
        return new ResponseEntity<>(new AddGradeResponse("ok"),HttpStatus.OK);
    }
    @GetMapping(path="/getclassgrade")
    public ResponseEntity<GetGradeResponse> getgrade(@RequestBody GetGradeRequest request)
    {
        List<Long> cids = request.getCid();
        List<Integer> scores = new ArrayList<>();
        for(Long cid:cids)
        {
            Optional<ClassEntity> retclass = classRepository.findById(cid);
            if(!retclass.isPresent())
            {
                return new ResponseEntity<>(new GetGradeResponse("class not exist"),HttpStatus.BAD_REQUEST);
            }
            ClassEntity classEntity =retclass.get();
            for(TakesEntity takesEntity:classEntity.getTakes())
            {
                if(takesEntity.getStudent().getUid().equals(request.getUid()))
                {
                    scores.add(takesEntity.getScore());
                }
            }
        }
        return new ResponseEntity<>(new GetGradeResponse("ok",scores),HttpStatus.OK);
    }
    @PostMapping(path="/modify")
    public ResponseEntity<ModifyGradeResponse> modifygrade(@RequestBody ModifyGradeRequest request)
    {
        Long cid = request.getCid();
        Optional<ClassEntity> ret = classRepository.findById(cid);
        if(!ret.isPresent())
        {
            return new ResponseEntity<>(new ModifyGradeResponse("no such class"),HttpStatus.BAD_REQUEST);
        }
        ClassEntity classEntity=ret.get();
        for(TakesEntity takesEntity:classEntity.getTakes())
        {
            if(takesEntity.getStudent().getUid().equals(request.getStudentid()))
            {
                ModifyEntity modifyEntity = new ModifyEntity();
                modifyEntity.setCid(request.getCid());
                modifyEntity.setReasons(request.getReasons());
                modifyEntity.setScore(request.getScore());
                modifyEntity.setUid(request.getStudentid());
                modifyEntity.setStatus(false);
                modifyEntity.setProcess(false);
                modifyRepository.save(modifyEntity);
                return new ResponseEntity<>(new ModifyGradeResponse("modify action has been sumited"),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ModifyGradeResponse("no such student in this class"),HttpStatus.BAD_REQUEST);
    }
    @PostMapping(path = "/getprocessmodify")
    public ResponseEntity<GetProcessResponse>getprocess(@RequestBody GetProcessRequest request)
    {
        String uid = request.getUid();
        Optional<UserEntity> ret= userRepository.findById(uid);
        if(!ret.isPresent())
        {
            return new ResponseEntity<>(new GetProcessResponse("no such adminstrator"),HttpStatus.BAD_REQUEST);
        }
        UserEntity adminstrator = ret.get();
        Iterable<ModifyEntity> modifyEntities = modifyRepository.findAll();
        List<String> reasons = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        List<String> uids = new ArrayList<>();
        List<Long> cids = new ArrayList<>();
        for(ModifyEntity modifyEntity :modifyEntities)
        {
            reasons.add(modifyEntity.getReasons());
            scores.add(modifyEntity.getScore());
            uids.add(modifyEntity.getUid());
            cids.add(modifyEntity.getCid());
        }
        return new ResponseEntity<>(new GetProcessResponse("ok",reasons,scores,uids,cids),HttpStatus.OK);
    }
    @PostMapping(path = "/processmodify")
    public ResponseEntity<ProcessModifyResponse>processmodify(@RequestBody ProcessModifyRequest request) {
        if (request.getProcess()) {
            Long cid = request.getCids();
            Optional<ClassEntity> ret = classRepository.findById(cid);
            if (!ret.isPresent())
                return new ResponseEntity<>(new ProcessModifyResponse("no such class"), HttpStatus.BAD_REQUEST);
            ClassEntity classEntity = ret.get();
            for (TakesEntity takesEntity : classEntity.getTakes()) {
                if (takesEntity.getStudent().getUid().equals(request.getUids())) {
                    takesEntity.setScore(request.getScore());
                    break;
                }
            }
            classRepository.save(classEntity);
        }
            return new ResponseEntity<>(new ProcessModifyResponse("ok"), HttpStatus.OK);

    }
    @GetMapping(path = "/getstudentclass")
    public ResponseEntity<GetStudentClassResponse>getstudentclass(@RequestBody GetStudentClassRequest request)
    {
        String uid = request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent())
        {
            return new ResponseEntity<>(new GetStudentClassResponse("no such student"),HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = ret.get();
        Iterable<TakesEntity> takesEntities = takesRepository.findAll();
        List<String> course = new ArrayList<>();
        List<Long> class_id = new ArrayList<>();
        for(TakesEntity takesEntity : takesEntities)
        {
            if(takesEntity.getStudent().getUid().equals(uid))
            {
                course.add(takesEntity.getClassEntity().getCourse().getName());
                class_id.add(takesEntity.getClassEntity().getId());
            }
        }
        return new ResponseEntity<>(new GetStudentClassResponse("ok",course,class_id),HttpStatus.OK);
    }
}
