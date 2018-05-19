package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.entities.PaperContainsQuestionEntity;
import tss.entities.PapersEntity;
import tss.entities.QuestionEntity;
import tss.repositories.PaperContainsQuestionRepository;
import tss.repositories.PaperRepository;
import tss.repositories.QuestionRepository;
import tss.requests.information.AddPaperRequest;
import tss.requests.information.DeletePaperRequest;
import tss.requests.information.GetPaperRequest;
import tss.requests.information.ModifyPaperRequest;
import tss.responses.information.AddPaperResponse;
import tss.responses.information.DeletePaperResponse;
import tss.responses.information.GetPaperResponse;
import tss.responses.information.ModifyPaperResponse;

import java.util.*;

@Controller
@RequestMapping(path = "testsys_paper")
public class PaperController {
    private final PaperRepository paperRepository;
    private final QuestionRepository questionRepository;
    private final PaperContainsQuestionRepository paperContainsQuestionRepository;

    @Autowired
    public PaperController(PaperRepository paperRepository, QuestionRepository questionRepository, PaperContainsQuestionRepository paperContainsQuestionRepository) {
        this.paperRepository = paperRepository;
        this.questionRepository = questionRepository;
        this.paperContainsQuestionRepository = paperContainsQuestionRepository;
    }

    @PostMapping(path = "/insert")
    public ResponseEntity<AddPaperResponse> addPaper(@RequestBody AddPaperRequest request) {
        if (paperRepository.existsById(request.getPid())) {
            return new ResponseEntity<>(new AddPaperResponse("Failed with duplicate pid", ""), HttpStatus.BAD_REQUEST);
        }

        PapersEntity paper = new PapersEntity();
        Set<PaperContainsQuestionEntity> paperQuestions = new HashSet<>();


        paper.setPid(request.getPid());
        paper.setPapername(request.getPapername());
        paper.setIsauto(request.getIsauto());
        paper.setBegin(request.getBegin());
        paper.setEnd(request.getEnd());
        paper.setLast(request.getLast());
        paper.setCount(request.getCount());

        paper.setAnswerednum(0);
        paper.setAverage(0.0);

        for (int i = 0; i < Integer.valueOf(request.getCount()); i++) {
            PaperContainsQuestionEntity contain = new PaperContainsQuestionEntity();

            int tempid = Integer.valueOf(request.getPid()) * 10000 + i;
            contain.setId(String.valueOf(tempid));
            contain.setPaper(paper);
            contain.setScore(request.getScore()[i]);

            //setQuestion
            Optional<QuestionEntity> ret = questionRepository.findById(request.getQid()[i]);
            if (!ret.isPresent()) {
                return new ResponseEntity<>(new AddPaperResponse("non-exist qid", paper.getPid()), HttpStatus.BAD_REQUEST);
            }
            QuestionEntity question = ret.get();

            contain.setQuestion(question);

            paperQuestions.add(contain);
            paperContainsQuestionRepository.save(contain);

        }

        paper.setPaperquestion(paperQuestions);

        paperRepository.save(paper);
        System.out.println("insert paper" + request.getPid());

        return new ResponseEntity<>(new AddPaperResponse("ok", paper.getPid()), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<DeletePaperResponse> deletePaper(@RequestBody DeletePaperRequest request) {
        String pid = request.getPid();
        if (!paperRepository.existsById(pid)) {
            return new ResponseEntity<>(new DeletePaperResponse("Paper does not exist"), HttpStatus.BAD_REQUEST);
        }
        paperRepository.deleteById(pid);

        return new ResponseEntity<>(new DeletePaperResponse("ok"), HttpStatus.OK);
    }


    @PostMapping(path = "/update")
    public ResponseEntity<ModifyPaperResponse> modifyPaper(@RequestBody ModifyPaperRequest request) {
        String pid = request.getPid();
        Optional<PapersEntity> ret = paperRepository.findById(pid);
        if (!ret.isPresent()) {
            System.out.println("Paper does not exist:" + request.getPid());
            return new ResponseEntity<>(new ModifyPaperResponse("Paper does not exist", request.pid), HttpStatus.BAD_REQUEST);
        }
        PapersEntity paper = ret.get();

        Set<PaperContainsQuestionEntity> paperquestions = new HashSet<>();

        paper.setPid(request.getPid());
        paper.setPapername(request.getPapername());
        paper.setIsauto(request.getIsauto());
        paper.setBegin(request.getBegin());
        paper.setEnd(request.getEnd());
        paper.setLast(request.getLast());
        paper.setCount(request.getCount());

        paper.setAnswerednum(0);
        paper.setAverage(0.0);

        System.out.println("count:" + request.getCount());
        for (int i = 0; i < Integer.valueOf(request.getCount()); i++) {
            PaperContainsQuestionEntity contain = new PaperContainsQuestionEntity();

            int tempid = Integer.valueOf(request.getPid()) * 10000 + i;
            contain.setId(String.valueOf(tempid));
            contain.setPaper(paper);
            contain.setScore(request.getScore()[i]);

            //setQuestion
            Optional<QuestionEntity> ret2 = questionRepository.findById(request.getQid()[i]);
            if (!ret2.isPresent()) {
                return new ResponseEntity<>(new ModifyPaperResponse("non-exist qid", paper.getPid()), HttpStatus.BAD_REQUEST);
            }
            QuestionEntity question = ret2.get();

            contain.setQuestion(question);

            paperquestions.add(contain);
            paperContainsQuestionRepository.save(contain);

        }

        paper.setPaperquestion(paperquestions);

        paperRepository.save(paper);
        System.out.println("update paper" + request.getPid());


        return new ResponseEntity<>(new ModifyPaperResponse("ok", request.pid), HttpStatus.OK);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<GetPaperResponse> searchPaper(@RequestBody GetPaperRequest request) {
        List<PapersEntity> papers = new ArrayList<>();

        String type = request.getDirection();
        if (type.equals("all")) {
            Iterable<PapersEntity> paper_find = paperRepository.findAll();
            for (PapersEntity paper : paper_find) {
                papers.add(paper);
            }
        } else {
            System.out.println("Invalid direction: " + type);
            return new ResponseEntity<>(new GetPaperResponse("Invalid direction", papers), HttpStatus.BAD_REQUEST);
        }


        System.out.println("search: " + type);
        return new ResponseEntity<>(new GetPaperResponse("ok", papers), HttpStatus.OK);
    }
}
