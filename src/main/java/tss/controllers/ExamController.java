package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.CurrentUser;
import tss.entities.*;
import tss.information.PaperResponseStruct;
import tss.information.untapped.*;
import tss.repositories.*;
import tss.requests.information.AddResultRequest;
import tss.requests.information.DeleteResultRequest;
import tss.requests.information.GetPaperRequest;
import tss.responses.information.AddResultResponse;
import tss.responses.information.DeleteResultResponse;
import tss.responses.information.GetPaperResponse;

import javax.swing.text.html.Option;
import javax.xml.transform.Result;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

//result Rid: pid+uid+qid
//historygrade hid: uid+pid
@Controller
@RequestMapping(path = "testsys_student")
public class ExamController {

    private final PaperRepository paperRepository;
    private final QuestionRepository questionRepository;
    private final PaperContainsQuestionRepository paperContainsQuestionRepository;
    private final HistoryGradeRepository historyGradeRepository;
    private final ResultRepository resultRepository;

    @Autowired
    public ExamController(PaperRepository paperRepository, QuestionRepository questionRepository,
                          PaperContainsQuestionRepository paperContainsQuestionRepository,
                          HistoryGradeRepository historyGradeRepository, ResultRepository resultRepository) {
        this.paperRepository = paperRepository;
        this.questionRepository = questionRepository;
        this.paperContainsQuestionRepository = paperContainsQuestionRepository;
        this.historyGradeRepository = historyGradeRepository;
        this.resultRepository = resultRepository;
    }

    @PostMapping(path = "/getpaperlist")
    public ResponseEntity<ShowPapersResponse> ShowPapers(@RequestBody ShowPapersRequest request){
       // List<PaperResponseStruct> papers = new ArrayList<>();
        Date nowdate= new Date();
        List<String> pid = new ArrayList<String>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datebegin = new Date();
        Date dateend = new Date();

        Iterable<PapersEntity> paper_find = paperRepository.findAll();
        for(PapersEntity paper : paper_find){
           /* PaperResponseStruct paper_return = new PaperResponseStruct();
            paper_return.setPid(paper.getPid());
            paper_return.setBegin(paper.getBegin());
            paper_return.setEnd(paper.getEnd());
            paper_return.setCount(paper.getCount());
            paper_return.setIsauto(paper.getIsauto());
            paper_return.setLast(paper.getLast());
            paper_return.setPapername(paper.getPapername());
            paper_return.setQid(null);
            paper_return.setScore(null); */

            try {
                datebegin = formatter.parse(paper.getBegin());
                dateend = formatter.parse(paper.getEnd());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(nowdate.after(datebegin)&& nowdate.before(dateend)){
                pid.add(paper.getPid());
            }
        }
        System.out.println("ok");
        return new ResponseEntity<>(new ShowPapersResponse("ok", pid), HttpStatus.OK);
    }



    @PostMapping(path = "/getpaper")
    public ResponseEntity<SelectPaperResponse> SelectPaper(@RequestBody SelectPaperRequest request){
        PaperResponseStruct paper_return;
        paper_return = new PaperResponseStruct();

        Optional<PapersEntity> ret= paperRepository.findById(request.getPid());
        if(!ret.isPresent()){
            System.out.println("Paper does not exist:"+request.getPid());
            return new ResponseEntity<>(new SelectPaperResponse("Paper does not exist", null), HttpStatus.BAD_REQUEST);
        }
        PapersEntity paper = ret.get();

        paper_return.setPid(paper.getPid());
        paper_return.setBegin(paper.getBegin());
        paper_return.setEnd(paper.getEnd());
        paper_return.setCount(paper.getCount());
        paper_return.setIsauto(paper.getIsauto());
        paper_return.setLast(paper.getLast());
        paper_return.setPapername(paper.getPapername());
        paper_return.setQid(null);
        paper_return.setScore(null);

        System.out.println("ok");
        return new ResponseEntity<>(new SelectPaperResponse("ok", paper_return), HttpStatus.OK);
    }


    @PostMapping(path = "/getquestions")
    public ResponseEntity<StartExamResponse> StartExam(@CurrentUser UserEntity user, @RequestBody StartExamRequest request){
        PaperResponseStruct paper_return;
        HistoryGradeEntity graderecord=new HistoryGradeEntity();

        paper_return = new PaperResponseStruct();
        Date nowdate =new Date();
        String[] qid;
        String[] score;
        boolean exist;

        Optional<HistoryGradeEntity> ret= historyGradeRepository.findById(user.getUid()+request.getPid());
        if(exist=ret.isPresent()){
            graderecord = ret.get();
            if(graderecord.getGrade()!=-1){ //还在考试的过程中
                String starttime= graderecord.getStartTime().toString();
                System.out.println("the paper expired");
                return new ResponseEntity<>(new StartExamResponse("the paper expired", null, starttime),HttpStatus.BAD_REQUEST);
            }
        }

        Optional<PapersEntity> ret2= paperRepository.findById(request.getPid());
        PapersEntity paper = ret2.get();
        paper_return = new PaperResponseStruct();
        paper_return.setPid(paper.getPid());
        paper_return.setBegin(paper.getBegin());
        paper_return.setEnd(paper.getEnd());
        paper_return.setCount(paper.getCount());
        paper_return.setIsauto(paper.getIsauto());
        paper_return.setLast(paper.getLast());
        paper_return.setPapername(paper.getPapername());

        int count = 0;

        List<PaperContainsQuestionEntity> contain_find = paperContainsQuestionRepository.findByPaper(paper);
        qid = new String[contain_find.size()];
        score = new String[contain_find.size()];

        for(PaperContainsQuestionEntity contain:contain_find){
            qid[count] = contain.getQuestion().getQid();
            score[count] = contain.getScore();
            count++;
        }

        paper_return.setQid(qid);
        paper_return.setScore(score);


        if(!exist) {
            graderecord.setGrade(-1);
            graderecord.setPaper(paper);
            graderecord.setStudent(user);
            graderecord.setHid(user.getUid()+paper.getPid());
            graderecord.setStartTime(nowdate);

            historyGradeRepository.save(graderecord);
        }

        System.out.println("ok");
        return new ResponseEntity<>(new StartExamResponse("ok", paper_return, nowdate.toString()), HttpStatus.OK);

    }

    @PostMapping(path = "/save")
    public ResponseEntity<AddResultResponse> SavePaper(@CurrentUser UserEntity user, @RequestBody AddResultRequest request){
        ResultEntity result= new ResultEntity();
        QuestionEntity question;
        Optional<PapersEntity> ret= paperRepository.findById(request.getPid());
        Optional<QuestionEntity> ret2;
        if(!ret.isPresent()){
            System.out.println("can't save the paper");
            return  new ResponseEntity<>(new AddResultResponse("can't save the paper"), HttpStatus.BAD_REQUEST);
        }
        PapersEntity paper = ret.get();
        result.setStudent(user);
        result.setPaper(paper);
        int count = Integer.parseInt(paper.getCount());
        for(int i=0; i<count;i++){
            ret2= questionRepository.findById(request.getQid()[i]);
            if(!ret2.isPresent()){
                System.out.println("Can't save question "+ i);
                return  new ResponseEntity<>(new AddResultResponse("can't save question "+i), HttpStatus.BAD_REQUEST);
            }
            question = ret2.get();
            result.setQuestion(question);
            result.setAns(request.getAns()[i]);
            result.setRid(paper.getPid()+user.getUid()+question.getQid());
            resultRepository.save(result);
        }

        return new ResponseEntity<>(new AddResultResponse("paper saved"), HttpStatus.OK);
    }


    @PostMapping(path = "/submit")
    public ResponseEntity<DeleteResultResponse> SubmitPaper(@CurrentUser UserEntity user, @RequestBody DeleteResultRequest request){
// ret: paper     ret2: result  ret3: Question ret4: historygrade
        PapersEntity paper;
        ResultEntity result;
        QuestionEntity question;
        HistoryGradeEntity record;
        int totalscore=0;
        Optional<PapersEntity> ret = paperRepository.findById(request.getPid());
        if(!ret.isPresent()){
            System.out.println("can't submit the paper");
            return  new ResponseEntity<>(new DeleteResultResponse("can't submit the paper"), HttpStatus.BAD_REQUEST);
        }


        paper = ret.get();// 取得当前的试卷
        int count = Integer.parseInt(paper.getCount());
        PaperContainsQuestionEntity contain;
        Optional<ResultEntity> ret2;
        Optional<QuestionEntity> ret3;
        Optional<HistoryGradeEntity> ret4;

        Iterator<PaperContainsQuestionEntity> contain_find = paper.getPaperquestion().iterator();

        while(contain_find.hasNext()){ //对卷子中的每一道题，查找result中相应的结果， 更新 qustion库中的question

            contain = contain_find.next();//contain 是当前卷子中的题目
            ret2 = resultRepository.findById(paper.getPid()+user.getUid()+contain.getQuestion().getQid());
            if(!ret2.isPresent()){
                System.out.println("submit question error");
                return new ResponseEntity<>(new DeleteResultResponse("submit question error"), HttpStatus.BAD_REQUEST);
            }
            result = ret2.get();

            ret3 = questionRepository.findById(contain.getQuestion().getQid());
            if(!ret3.isPresent()){
                System.out.println("submit question error");
                return new ResponseEntity<>(new DeleteResultResponse("submit question error"), HttpStatus.BAD_REQUEST);
            }
            question = ret3.get();

            question.setAnswerednum(question.getAnswerednum()+1);//修改题目的答题数
            if(question.getQanswer().equals(result.getAns())){
                totalscore = totalscore+ Integer.parseInt(contain.getScore());
                question.setCorrect(question.getCorrect()+1);//修改正确的题目数量
            }
            questionRepository.save(question);//存回修改了的题目信息
            resultRepository.deleteById(paper.getPid()+user.getUid()+contain.getQuestion().getQid());
        }

        ret4 = historyGradeRepository.findById(user.getUid()+paper.getPid());
        if(!ret4.isPresent()){
            System.out.println("cannot grading");
            return new ResponseEntity<>(new DeleteResultResponse("cannot grading"), HttpStatus.BAD_REQUEST);
        }
        record = ret4.get();
        record.setGrade(totalscore);
        historyGradeRepository.save(record);

        double oldavg = paper.getAverage();
        long oldanswered = paper.getAnswerednum();
        paper.setAverage((oldanswered*oldavg+totalscore)/(oldanswered+1));
        paper.setAnswerednum(oldanswered+1);
        paperRepository.save(paper);

        return  new ResponseEntity<>(new DeleteResultResponse("Submit Ok"),HttpStatus.OK);
    }















}