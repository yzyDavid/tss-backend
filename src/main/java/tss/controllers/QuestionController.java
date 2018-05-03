package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.QuestionEntity;
import tss.entities.UserEntity;
import tss.repositories.QuestionRepository;
import tss.requests.information.AddQuestionRequest;
import tss.responses.information.AddCourseResponse;
import tss.responses.information.AddQuestionResponse;

@Controller
@RequestMapping(path = "/testsys_question")
public class QuestionController {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    @PutMapping(path = "/insert")
    @Authorization
    public ResponseEntity<AddQuestionResponse>addQuestion(@CurrentUser UserEntity user,
                                                          @RequestBody AddQuestionRequest request){
        int usertype = user.getType();
        if(usertype != UserEntity.TYPE_MANAGER && usertype != UserEntity.TYPE_TEACHER){
            return new ResponseEntity<>(new AddQuestionResponse("permission denied", ""), HttpStatus.FORBIDDEN);
        }else if(questionRepository.existsById(request.getQid())){
            return new ResponseEntity<>(new AddQuestionResponse("failed with duplicate qid",""), HttpStatus.BAD_REQUEST);
        }
        QuestionEntity question = new QuestionEntity();
        question.setId(request.getQid());
        question.setQuestion(request.getQuestion());
        question.setQAanswer(request.getQanswer());
        question.setQtype(request.getQtype());
        question.setQunit(request.getQunit());
        question.setAnswerednum(0);
        question.setCorrect(0.0);

        questionRepository.save(question);

        return new ResponseEntity<>(new AddQuestionResponse("ok", question.getId()),  HttpStatus.CREATED);
    }

}
