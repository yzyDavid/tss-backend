package tss.controllers.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.ClassEntity;
import tss.entities.TeachesEntity;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsSectionEntity;
import tss.repositories.TeachesRepository;
import tss.repositories.bbs.BbsSectionRepository;
import tss.requests.information.bbs.AddBbsSectionRequest;
import tss.responses.information.bbs.AddBbsSectionResponse;

import java.util.Optional;
import java.util.Set;

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

}
