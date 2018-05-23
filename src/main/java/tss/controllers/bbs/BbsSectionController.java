package tss.controllers.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.ClassEntity;
import tss.entities.TeachesEntity;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsSectionEntity;
import tss.entities.bbs.BbsTopicEntity;
import tss.repositories.TeachesRepository;
import tss.repositories.bbs.BbsSectionRepository;
import tss.requests.information.bbs.AddBbsSectionRequest;
import tss.requests.information.bbs.DeleteBbsSectionRequest;
import tss.responses.information.bbs.*;

import java.util.*;

@Controller
@RequestMapping(path = "/section")
public class BbsSectionController {
    private final BbsSectionRepository bbsSectionRepository;
    private final TeachesRepository teachesRepository;

    @Autowired
    public BbsSectionController(BbsSectionRepository bbsSectionRepository,
                                TeachesRepository teachesRepository){
        this.bbsSectionRepository = bbsSectionRepository;
        this.teachesRepository = teachesRepository;
    }

    /* create a section
     * request: id, name, teacher id
     * permission : manager
     * return : id, name, teacher name
     */
    @PutMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddBbsSectionResponse> addBbsSection(@CurrentUser UserEntity user,
                                                               @RequestBody AddBbsSectionRequest request){
        /* every section init bind a teacher */
        /* test the exit of teacher */
        String teacherID = request.getTid();
        Optional<TeachesEntity> ret = teachesRepository.findById(Long.valueOf(teacherID));
        if(!ret.isPresent())
            return new ResponseEntity<>(new AddBbsSectionResponse("no such teacher", -1, null, null), HttpStatus.BAD_REQUEST);
        TeachesEntity teaches = ret.get();

        long id = request.getId();
        String name = request.getName();

        /* permission and duplicated error */
        if(user.getType() != UserEntity.TYPE_MANAGER)
            return new ResponseEntity<>(new AddBbsSectionResponse("permission denied", -1, null, null), HttpStatus.FORBIDDEN);
        else if(bbsSectionRepository.existsById(id))
            return new ResponseEntity<>(new AddBbsSectionResponse("failed with duplicated id", -1, null, null), HttpStatus.BAD_REQUEST);
        else if(bbsSectionRepository.existsByName(name))
            return new ResponseEntity<>(new AddBbsSectionResponse("failed with duplicated id", -1, null, null), HttpStatus.BAD_REQUEST);

        /* init the new section */
        BbsSectionEntity section = new BbsSectionEntity(teaches);
        section.setId(id);
        section.setName(name);

        /* sum the user number according to the teacher */
        Set<ClassEntity> classes = teaches.getClasses();
        for(ClassEntity c : classes)
            BbsSectionEntity.usrNum += c.getStudentNum();

        bbsSectionRepository.save(section);
        return new ResponseEntity<>(new AddBbsSectionResponse("add ok", id, name, teaches.getTeacher().getName()), HttpStatus.OK);
    }

    /* delete a section with id
     * request: id
     * permission: manager
     * return: id, name
     */
    @DeleteMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteBbsSectionResponse> deleteBbsSection(@CurrentUser UserEntity user,
                                                                     @RequestBody DeleteBbsSectionRequest request){
        long id = request.getId();
        Optional<BbsSectionEntity> ret = bbsSectionRepository.findById(id);

        /* permission error */
        if(user.getType() != UserEntity.TYPE_MANAGER)
            return new ResponseEntity<>(new DeleteBbsSectionResponse("permission denied", -1, null), HttpStatus.FORBIDDEN);

        /* no such section with request id */
        if(!ret.isPresent())
            return new ResponseEntity<>(new DeleteBbsSectionResponse("no such section id", -1, null), HttpStatus.BAD_REQUEST);

        BbsSectionEntity section = ret.get();
        bbsSectionRepository.delete(section);
        return new ResponseEntity<>(new DeleteBbsSectionResponse("ok", section.getId(), section.getName()), HttpStatus.OK);
    }

    /* show all sections information, no need permission
     * permission: anyone
     * return: List ids , List names
     * v1.0, done
     */
    @GetMapping(path = "/info")
    public ResponseEntity<GetInfoBbsSectionResponse> infoBbsSection(){
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();

        Iterator<BbsSectionEntity> iter = bbsSectionRepository.findAll().iterator();
        while(iter.hasNext()){
           Long id = iter.next().getId();
           String name = iter.next().getName();
           ids.add(id.toString());
           names.add(name);
        }

        /* empty in sections */
        if(ids.isEmpty())
            return new ResponseEntity<>(new GetInfoBbsSectionResponse(null, null), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new GetInfoBbsSectionResponse(ids, names), HttpStatus.OK);
    }


    /* to do: modify, many add section introduction part */

}
