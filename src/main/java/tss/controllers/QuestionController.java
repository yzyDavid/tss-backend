package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.CourseEntity;
import tss.entities.QuestionEntity;
import tss.entities.UserEntity;
import tss.repositories.QuestionRepository;
import tss.repositories.SqlSessionRepository;
import tss.requests.information.AddQuestionRequest;
import tss.requests.information.DeleteQuestionRequest;
import tss.requests.information.ModifyQuestionRequest;
import tss.responses.information.AddCourseResponse;
import tss.responses.information.AddQuestionResponse;
import tss.responses.information.DeleteQuestionResponse;
import tss.responses.information.ModifyQuestionResponse;

import java.util.Optional;

@Controller
@RequestMapping(path = "/testsys_question")
public class QuestionController {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }


    @PostMapping(path = "/insert")
    public ResponseEntity<AddQuestionResponse>addQuestion(@RequestBody AddQuestionRequest request){


        if(questionRepository.existsById(request.getQid())){
            return new ResponseEntity<>(new AddQuestionResponse("Failed with duplicate qid",""), HttpStatus.BAD_REQUEST);
        }
        /*
        int usertype = user.getType();
        else if(usertype != UserEntity.TYPE_MANAGER && usertype != UserEntity.TYPE_TEACHER){
            return new ResponseEntity<>(new AddQuestionResponse("permission denied", ""), HttpStatus.FORBIDDEN);
        }
        */
        QuestionEntity question = new QuestionEntity();
        question.setQid(request.getQid());
        question.setQuestion(request.getQuestion());
        question.setQanswer(request.getQanswer());
        question.setQtype(request.getQtype());
        question.setQunit(request.getQunit());
        question.setAnswerednum(0);
        question.setCorrect(0.0);

        questionRepository.save(question);
        System.out.println("insert question"+request.getQid());
        return new ResponseEntity<>(new AddQuestionResponse("ok", question.getQid()),  HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<DeleteQuestionResponse>deleteQuestion(@RequestBody DeleteQuestionRequest request){
        String qid = request.getQid();
        if(!questionRepository.existsById(qid)){
            return new ResponseEntity<>(new DeleteQuestionResponse("Question does not exist"), HttpStatus.BAD_REQUEST);
        }
        /*
        int usertype = user.getType();
        else if(usertype != UserEntity.TYPE_MANAGER && usertype != UserEntity.TYPE_TEACHER){
            return new ResponseEntity<>(new DeleteQuestionResponse("permission denied", ""), HttpStatus.FORBIDDEN);
        }
        */
        questionRepository.deleteById(qid);

        return  new ResponseEntity<>(new DeleteQuestionResponse("ok"), HttpStatus.OK);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<ModifyQuestionResponse>modifyQuestion(@RequestBody ModifyQuestionRequest request){

        String qid = request.getQid();
        /*
        int usertype = user.getType();
        else if(usertype != UserEntity.TYPE_MANAGER && usertype != UserEntity.TYPE_TEACHER){
            return new ResponseEntity<>(new ModifyQuestionResponse("permission denied", ""), HttpStatus.FORBIDDEN);
        }
        */
        Optional<QuestionEntity> ret = questionRepository.findById(qid);
        if(!ret.isPresent()){
            return new ResponseEntity<>(new ModifyQuestionResponse("Question does not exist",""),HttpStatus.BAD_REQUEST);
        }
        QuestionEntity question = ret.get();

        question.setQuestion(request.getQuestion());
        question.setQanswer(request.getQanswer());
        question.setQtype(request.getQtype());
        question.setQunit(request.getQunit());
        question.setAnswerednum(0);
        question.setCorrect(0.0);

        questionRepository.save(question);
        System.out.println("update question"+request.getQid());
        return new ResponseEntity<>(new ModifyQuestionResponse("ok", question.getQid()),  HttpStatus.CREATED);

    }
}
