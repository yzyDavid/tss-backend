package tss.controllers.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsSectionEntity;
import tss.repositories.bbs.BbsSectionRepository;
import tss.requests.information.bbs.AddBbsSectionRequest;
import tss.requests.information.bbs.DeleteBbsSectionRequest;
import tss.responses.information.bbs.AddBbsSectionResponse;
import tss.responses.information.bbs.DeleteBbsSectionResponse;
import tss.responses.information.bbs.GetInfoBbsSectionResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/section")
public class BbsSectionController {
    private final BbsSectionRepository bbsSectionRepository;

    @Autowired
    public BbsSectionController(BbsSectionRepository bbsSectionRepository) {
        this.bbsSectionRepository = bbsSectionRepository;
    }

    /**
     * create a section
     * request: id, name, teacher id
     * permission : manager
     * return : id, name, teacher name
     * maybe no use
     */
    @PostMapping(path = "/add")
    public ResponseEntity<AddBbsSectionResponse> addBbsSection( @RequestBody AddBbsSectionRequest request) {
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


    /* to do: modify, many add section introduction part */

}
