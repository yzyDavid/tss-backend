package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import tss.annotations.session.CurrentUser;
import tss.entities.*;
import tss.repositories.*;
import tss.requests.information.GetGradeRequest;
import tss.responses.information.GetGradeBySidResponse;
import tss.responses.information.GetGradeResponse;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class GradeController {


    private final PaperRepository paperRepository;
    private final QuestionRepository questionRepository;
    //private final PaperContainsQuestionRepository paperContainsQuestionRepository;
    private final HistoryGradeRepository historyGradeRepository;
   // private final ResultRepository resultRepository;

    @Autowired
    public GradeController(PaperRepository paperRepository, QuestionRepository questionRepository, HistoryGradeRepository historyGradeRepository) {
        this.paperRepository = paperRepository;
        this.questionRepository = questionRepository;
        this.historyGradeRepository = historyGradeRepository;
    }

    public ResponseEntity<GetGradeBySidResponse> StudentGrade(@CurrentUser UserEntity user, @RequestBody GetGradeBySidResponse request){
        List<String> pid = new ArrayList<String>();
        List<String> score = new ArrayList<String>();

        Iterable<HistoryGradeEntity> records_find =  historyGradeRepository.findByStudent(user.getUid());
        if(((List<HistoryGradeEntity>) records_find).isEmpty()){
            return new ResponseEntity<>(new GetGradeBySidResponse("This student haven't take any exam yet", user.getUid(), null, null), HttpStatus.BAD_REQUEST);
        }
        int i=0;
        int size = ((List<HistoryGradeEntity>) records_find).size();

        for(HistoryGradeEntity record : records_find){
            pid.add(i,record.getPaper().getPid());
            score.add(i, Integer.toString(record.getGrade()));
            i++;
        }
        return  new ResponseEntity<>(new GetGradeBySidResponse("Ok", user.getUid(), pid, score),HttpStatus.OK);
    }

    public ResponseEntity<GetGradeResponse> GetGrade(@CurrentUser UserEntity user, @RequestBody GetGradeRequest request){

        GetGradeRequest.QueryType type = request.getType();
        List<String> qid  = new ArrayList<String>();
        List <Double>rate = new ArrayList<Double>();

        if(type.equals(GetGradeRequest.QueryType.PID)){
            Optional<PapersEntity> ret1 = paperRepository.findById(request.getPid());
            if(!ret1.isPresent()){
                System.out.println("No such a paper");
                return  new ResponseEntity<>(new GetGradeResponse("No such a paper", null, null), HttpStatus.BAD_REQUEST);
            }
            PapersEntity paper = ret1.get();//已经找到卷子了

            int i=0;
            double thisrate;
            PaperContainsQuestionEntity contain;
            QuestionEntity question;
            Iterator<PaperContainsQuestionEntity> contain_list = paper.getPaperquestion().iterator();
            while (contain_list.hasNext()){
                contain = contain_list.next();
                question = contain.getQuestion();
                qid.add(i, question.getQid());
                thisrate = question.getCorrect()/question.getAnswerednum();
                rate.add(i, thisrate);
                i++;
            }
            return new ResponseEntity<>(new GetGradeResponse("ok", qid, rate),HttpStatus.OK);
        }
        else {
            int i=0;
            double thisrate;
            Iterable<QuestionEntity> question_find;
            if(type.equals(GetGradeRequest.QueryType.QTYPE)){
                question_find = questionRepository.findByQtype(request.getQType());
            }
            else if(type.equals(GetGradeRequest.QueryType.QUNIT)){
                question_find = questionRepository.findByQunit(request.getQUnit());
            }
            else{
                System.out.println("Invalid request type");
                return new ResponseEntity<>(new GetGradeResponse("Invalid request type", null, null), HttpStatus.BAD_REQUEST);
            }

            for(QuestionEntity question : question_find){
                qid.add(i, question.getQid());
                thisrate = question.getCorrect()/question.getAnswerednum();
                rate.add(i, thisrate);
                i++;
            }
            return new ResponseEntity<>(new GetGradeResponse("ok", qid, rate), HttpStatus.OK);
        }


    }
}
