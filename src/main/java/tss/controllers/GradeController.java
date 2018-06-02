package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.*;
import tss.repositories.*;
import tss.requests.information.GetGradeRequest;
import tss.responses.information.GetGradeResponse;
import tss.responses.information.ModifyPaperResponse;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "testsys_result")
public class GradeController {

    private final UserRepository userRepository;
    private final PaperRepository paperRepository;
    private final QuestionRepository questionRepository;
    private final PaperContainsQuestionRepository paperContainsQuestionRepository;
    private final HistoryGradeRepository historyGradeRepository;
   // private final ResultRepository resultRepository;

    @Autowired
    public GradeController(PaperRepository paperRepository, QuestionRepository questionRepository, HistoryGradeRepository historyGradeRepository, UserRepository userRepository, PaperContainsQuestionRepository paperContainsQuestionRepository) {
        this.paperRepository = paperRepository;
        this.questionRepository = questionRepository;
        this.historyGradeRepository = historyGradeRepository;
        this.userRepository = userRepository;
        this.paperContainsQuestionRepository = paperContainsQuestionRepository;
    }

  /*  @PostMapping(path = "/insert")
    public ResponseEntity<GetGradeBySidResponse> StudentGrade(@CurrentUser UserEntity user, @RequestBody GetGradeBySidRequest request){
        List<String> pid = new ArrayList<String>();
        List<String> score = new ArrayList<String>();
        Iterable<HistoryGradeEntity> records_find;
        if(request.getSid()!=null) {
           records_find =  historyGradeRepository.findByStudent(request.getSid());
        }
        else{
           records_find =  historyGradeRepository.findByStudent(user.getUid());
        }

        if(((List<HistoryGradeEntity>) records_find).isEmpty()){
            return new ResponseEntity<>(new GetGradeBySidResponse("This student haven't take any exam yet", user.getUid(), null, null), HttpStatus.BAD_REQUEST);
        }
        int i=0;
        //int size = ((List<HistoryGradeEntity>) records_find).size();

        for(HistoryGradeEntity record : records_find){
            pid.add(i,record.getPaper().getPid());
            score.add(i, Integer.toString(record.getGrade()));
            i++;
        }
        return  new ResponseEntity<>(new GetGradeBySidResponse("Ok", user.getUid(), pid, score),HttpStatus.OK);
    }*/

    @PostMapping(path = "/search")
    @Authorization
    public ResponseEntity<GetGradeResponse> GetGrade(@CurrentUser UserEntity user, @RequestBody GetGradeRequest request){

        GetGradeRequest.QueryType type = request.getType();
        UserEntity student;

        if(type.equals(GetGradeRequest.QueryType.SID)){     //按学号
            List<String> pid = new ArrayList<String>();
            List<String> score = new ArrayList<String>();
            List<String> dates = new ArrayList<String>();
            Iterable<HistoryGradeEntity> records_find;
            if(request.getSid()!=null) {                    //老师查询
                Optional<UserEntity> ret = userRepository.findById(request.getSid());
                if(!ret.isPresent()){
                    return  new ResponseEntity<>(new GetGradeResponse("No such student", null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
                }
                student = ret.get();
                records_find =  historyGradeRepository.findByStudent(student);
            }
            else{               //学生查询
                Optional<UserEntity> ret = userRepository.findById(user.getUid());
                if(!ret.isPresent()){
                    return  new ResponseEntity<>(new GetGradeResponse("No such student", null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
                }
                student = ret.get();
                records_find =  historyGradeRepository.findByStudent(student);
            }
/*
            if(((List<HistoryGradeEntity>) records_find).isEmpty()){
                return new ResponseEntity<>(new GetGradeResponse("This student haven't take any exam yet", null, null, null, null), HttpStatus.BAD_REQUEST);
            }
            */
            int i=0;
            //int size = ((List<HistoryGradeEntity>) records_find).size();

            for(HistoryGradeEntity record : records_find){
                pid.add(i,record.getPaper().getPid());
                score.add(i, Integer.toString(record.getGrade()));
                dates.add(i, record.getStarttime().toString());
                i++;
            }
            return  new ResponseEntity<>(new GetGradeResponse("Ok", null, null, pid, score, dates, null),HttpStatus.OK);

        }
        else{
            List<String> qid  = new ArrayList<String>();
            List <Double>rate = new ArrayList<Double>();
            List<QuestionEntity>questions = new ArrayList<>();

            if(type.equals(GetGradeRequest.QueryType.PID)){
                Optional<PapersEntity> ret1 = paperRepository.findById(request.getPid());
                if(!ret1.isPresent()){
                    System.out.println("No such a paper");
                    return  new ResponseEntity<>(new GetGradeResponse("No such a paper", null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
                }
                PapersEntity paper = ret1.get();//已经找到卷子了
/*
                int i=0;
                double thisrate;
                PaperContainsQuestionEntity contain;
                QuestionEntity question;
                Iterator<PaperContainsQuestionEntity> contain_list = paper.getPaperquestion().iterator();
                while (contain_list.hasNext()){
                    contain = contain_list.next();
                    question = contain.getQuestion();
                    qid.add(i, question.getQid());
                    thisrate = (question.getAnswerednum() == 0)? 0 : question.getCorrect()/question.getAnswerednum();
                    rate.add(i, thisrate);
                    i++;
                }*/

                double thisrate;
                QuestionEntity question_t;
                List<PaperContainsQuestionEntity> contain_find = paperContainsQuestionRepository.findByPaper(paper);
                for(PaperContainsQuestionEntity contain:contain_find){
                    question_t = contain.getQuestion();
                    questions.add(question_t);
                    qid.add(question_t.getQid());
                    thisrate = (question_t.getAnswerednum() == 0)? 0 : question_t.getCorrect()/question_t.getAnswerednum();
                    rate.add(thisrate);

                }
                System.out.println(questions.size());
                return new ResponseEntity<>(new GetGradeResponse("ok", qid, rate, null,null, null, questions),HttpStatus.OK);
            }
            else {
                int i=0;
                double thisrate;
                Iterable<QuestionEntity> question_find;
                List<QuestionEntity>questionss = new ArrayList<>();

                if(type.equals(GetGradeRequest.QueryType.QTYPE)){
                    question_find = questionRepository.findByQtype(request.getQtype());
                }
                else if(type.equals(GetGradeRequest.QueryType.QUNIT)){
                    question_find = questionRepository.findByQunit(request.getQunit());
                }
                else{
                    System.out.println("Invalid request type");
                    return new ResponseEntity<>(new GetGradeResponse("Invalid request type", null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
                }

                for(QuestionEntity question : question_find){
                    questionss.add(question);
                    qid.add(question.getQid());
                    thisrate = (question.getAnswerednum() == 0) ? 0 : question.getCorrect()/question.getAnswerednum();
                    rate.add(thisrate);
                }

                return new ResponseEntity<>(new GetGradeResponse("ok", qid, rate, null, null, null, questionss), HttpStatus.OK);
            }
        }


    }
}
