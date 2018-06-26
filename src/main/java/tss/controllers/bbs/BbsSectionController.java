package tss.controllers.bbs;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.configs.Config;
import tss.entities.TakesEntity;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsSectionEntity;
import tss.entities.bbs.BbsTakeEntity;
import tss.repositories.UserRepository;
import tss.repositories.bbs.BbsSectionRepository;
import tss.repositories.bbs.BbsTakeRepository;
import tss.requests.information.bbs.AddBbsSectionRequest;
import tss.requests.information.bbs.AddSectionNoticeRequest;
import tss.requests.information.bbs.BbsBookRequest;
import tss.requests.information.bbs.DeleteBbsSectionRequest;
import tss.responses.information.bbs.*;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping(path = "/section")
public class BbsSectionController {
    private final BbsSectionRepository bbsSectionRepository;
    private final UserRepository userRepository;
    private final BbsTakeRepository bbstakeRepository;

    @Autowired
    public BbsSectionController(BbsSectionRepository bbsSectionRepository, UserRepository userRepository, BbsTakeRepository bbstakeRepository) {
        this.bbsSectionRepository = bbsSectionRepository;
        this.userRepository = userRepository;
        this.bbstakeRepository = bbstakeRepository;
    }

    /**
     * create a section
     * request: id, name, teacher id
     * permission : manager
     * return : id, name, teacher name
     * maybe no use
     */
    @PostMapping(path = "/add")
    public ResponseEntity<AddBbsSectionResponse> addBbsSection(@RequestBody AddBbsSectionRequest request) {
        BbsSectionEntity section = new BbsSectionEntity();
        section.setUsrNum(0);
        section.setName(request.getName());


        bbsSectionRepository.save(section);

        return new ResponseEntity<>(new AddBbsSectionResponse("ok", request.getId(), request.getName(), request.getTname()), HttpStatus.OK);
    }

    /**
     * delete a section with id
     * request: id
     * permission: manager
     * return: id, name
     */
    @DeleteMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteBbsSectionResponse> deleteBbsSection(@CurrentUser UserEntity user,
                                                                     @RequestBody DeleteBbsSectionRequest request) {
        long id = request.getId();
        Optional<BbsSectionEntity> ret = bbsSectionRepository.findById(id);

        /* permission error */
        // TODO

        /* no such section with request id */
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new DeleteBbsSectionResponse("no such section id", -1, null), HttpStatus.BAD_REQUEST);
        }

        BbsSectionEntity section = ret.get();
        bbsSectionRepository.delete(section);
        return new ResponseEntity<>(new DeleteBbsSectionResponse("ok", section.getId(), section.getName()), HttpStatus.OK);
    }

    /**
     * show all sections information, no need permission
     * permission: anyone
     * return: List ids , List names
     * v1.0, done
     */
    @GetMapping(path = "/info")
    public ResponseEntity<GetInfoBbsSectionResponse> infoBbsSection() {
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();

        Iterator<BbsSectionEntity> iter = bbsSectionRepository.findAll().iterator();
        while (iter.hasNext()) {
            BbsSectionEntity section = iter.next();
            Long id = section.getId();
            String name = section.getName();
            ids.add(id.toString());
            names.add(name);
        }


        /* empty in sections */
        if (ids.isEmpty()) {
            return new ResponseEntity<>(new GetInfoBbsSectionResponse(null, null), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new GetInfoBbsSectionResponse(ids, names), HttpStatus.OK);
    }


    /**
     * create a section notice
     * v1.0, done
     */
    @PostMapping(path = "/addnotice")
    //@Authorization
    public ResponseEntity<AddSectionNoticeResponse> addSectionNotice(//@CurrentUser UserEntity user,
                                                                     @RequestBody AddSectionNoticeRequest request) {
        Optional<BbsSectionEntity> ret = bbsSectionRepository.findById(Long.valueOf(request.getBoardID()));
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new AddSectionNoticeResponse("no such section!"), HttpStatus.BAD_REQUEST);
        }

        /* check permission, only manager can do*/
//        if (!Config.TYPES[1].equals(user.readTypeName())) {
//            return new ResponseEntity<>(new AddSectionNoticeResponse("permission denied"), HttpStatus.FORBIDDEN);
//        }

        BbsSectionEntity section = ret.get();
        section.setNotice(request.getBoardText());

        bbsSectionRepository.save(section);
        return new ResponseEntity<>(new AddSectionNoticeResponse("add notice ok"), HttpStatus.OK);
    }


    @PostMapping(path = "/book")
    //@Authorization
    public ResponseEntity<BbsBookResponse> bookSection(//@CurrentUser UserEntity user,
                                                       @RequestBody BbsBookRequest request) {
        UserEntity user = userRepository.findById("3150102242").get();
        BbsTakeEntity take = new BbsTakeEntity();
        take.setUid(user.getUid());
        /* find success to do */
        long sid = Long.valueOf(request.getBoardID());
        take.setSid(sid);

        bbstakeRepository.save(take);
        return new ResponseEntity<>(new BbsBookResponse("book ok"), HttpStatus.OK);
    }


    @PostMapping(path = "/unbook")
    //@Authorization
    public ResponseEntity<BbsBookResponse> unbookSection(//@CurrentUser UserEntity user,
                                                         @RequestBody BbsBookRequest request){
        UserEntity user = userRepository.findById("315012242").get();

        Optional<BbsSectionEntity> ret = bbsSectionRepository.findById(Long.valueOf(request.getBoardID()));
        if(!ret.isPresent()) {
            return new ResponseEntity<>(new BbsBookResponse("no such section!"), HttpStatus.BAD_REQUEST);
        }
        String uid = user.getUid();
        Long sid = ret.get().getId();

        BbsTakeEntity take = bbstakeRepository.findByUidAndSid(uid, sid).get();
        bbstakeRepository.delete(take);
        return new ResponseEntity<>(new BbsBookResponse("book ok"), HttpStatus.OK);
    }

    @GetMapping(path = "/showbook")
    //@Authorization
    public ResponseEntity<BbsShowBookResponse> showBookSection(//@CurrentUser UserEntity use
                                                                ){
        UserEntity user = userRepository.findById("3150102242").get();

        String uid = user.getUid();

        Set<BbsTakeEntity> takes = bbstakeRepository.findByUid(uid);
        List<String> boardNames = new ArrayList<>();
        List<String> boardIDs = new ArrayList<>();

        for(BbsTakeEntity t : takes){
            boardNames.add(bbsSectionRepository.findById(t.getSid()).get().getName());
            boardIDs.add(String.valueOf(t.getSid()));
        }
        return new ResponseEntity<>(new BbsShowBookResponse(boardNames, boardIDs), HttpStatus.OK);
    }
}
