package tss.controllers.bbs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.TeachesEntity;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsReplyEntity;
import tss.entities.bbs.BbsSectionEntity;
import tss.entities.bbs.BbsTopicEntity;
import tss.repositories.UserRepository;
import tss.repositories.bbs.BbsReplyRepository;
import tss.repositories.bbs.BbsTopicRepository;
import tss.requests.information.bbs.AddBbsReplyRequest;
import tss.requests.information.bbs.DeleteBbsReplyRequest;
import tss.requests.information.bbs.GetAllReplyRequest;
import tss.requests.information.bbs.ModifyReplyContentRequest;
import tss.responses.information.bbs.AddBbsReplyResponse;
import tss.responses.information.bbs.DeleteBbsReplyResponse;
import tss.responses.information.bbs.GetAllReplyResponse;
import tss.responses.information.bbs.ModifyReplyContentResponse;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(path = "/reply")
public class BbsReplyController {
    private final BbsTopicRepository bbsTopicRepository;
    private final UserRepository userRepository;
    private final BbsReplyRepository bbsReplyRepository;

    public BbsReplyController(BbsTopicRepository bbsTopicRepository, UserRepository userRepository, BbsReplyRepository bbsReplyRepository) {
        this.bbsTopicRepository = bbsTopicRepository;
        this.userRepository = userRepository;
        this.bbsReplyRepository = bbsReplyRepository;
    }

    /* create a new reply to a topic
     * request: id , topic id, content
     * permission: user in the section
     * return: id, topic name, content, time
     */
    @PostMapping(path = "add")
    @Authorization
    public ResponseEntity<AddBbsReplyResponse> addReply(@CurrentUser UserEntity user,
                                                        @RequestBody AddBbsReplyRequest request){
        /* permission error & invalid topic id error */
        long topicId = request.getTopic();
        Optional<BbsTopicEntity> ret = bbsTopicRepository.findById(topicId);
        if(!ret.isPresent())
            return new ResponseEntity<>(new AddBbsReplyResponse("no such topic", -1, null, null, null), HttpStatus.BAD_REQUEST);

        BbsTopicEntity topic = ret.get();

        /* duplicate id error */
        if(bbsReplyRepository.findById(request.getId()).isPresent())
            return new ResponseEntity<>(new AddBbsReplyResponse("duplicate reply id", -1, null, null, null), HttpStatus.BAD_REQUEST);

        BbsSectionEntity section = topic.getBelongedSection();
        TeachesEntity teaches = section.getTeaches();

        /* list user's teaches */
        boolean permission = false;
        Set<TeachesEntity> userTeaches = user.getTeaches();
        for(TeachesEntity t : userTeaches){
            if(t.getId() == teaches.getId()){
                permission = true;
                break;
            }
        }

        if(!permission)
            return new ResponseEntity<>(new AddBbsReplyResponse("permission error", -1, null, null, null), HttpStatus.FORBIDDEN);

        BbsReplyEntity reply = new BbsReplyEntity(user, topic);
        reply.setId(request.getId());
        reply.setContent(request.getContent());

        Date time = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mediumDateFormat.format(time);
        reply.setTime(time);

        bbsReplyRepository.save(reply);

        /* need to add the reply number in the topic */
        topic.setReplyNum(topic.getReplyNum()+1);

        return new ResponseEntity<>(new AddBbsReplyResponse("ok", reply.getId(), reply.getBelongedTopic().getName(),
                reply.getContent(), reply.getTime()), HttpStatus.OK);
    }


    /* delete a reply by id
     * request: id
     * permission: author, manager
     * return: id, author name
     */
    @DeleteMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteBbsReplyResponse> deleteReply(@CurrentUser UserEntity user,
                                                              @RequestBody DeleteBbsReplyRequest request){
        /* invalid id error */
        Optional<BbsReplyEntity> ret = bbsReplyRepository.findById(request.getId());
        if(!ret.isPresent())
            return new ResponseEntity<>(new DeleteBbsReplyResponse("no such reply", -1, null), HttpStatus.BAD_REQUEST);
        BbsReplyEntity reply = ret.get();

        /* permission error */
        if(user.getType() != UserEntity.TYPE_MANAGER ||
                !user.getUid().equals(reply.getAuthor().getUid()))
            return new ResponseEntity<>(new DeleteBbsReplyResponse("permission error", -1, null), HttpStatus.FORBIDDEN);

        bbsReplyRepository.delete(reply);

        /* need to add the reply number in the topic */
        reply.getBelongedTopic().setReplyNum(reply.getBelongedTopic().getReplyNum()-1);

        return new ResponseEntity<>(new DeleteBbsReplyResponse("ok", reply.getId(), reply.getAuthor().getName()), HttpStatus.OK);
    }


    /* modify a reply content by id
     * request: id
     * permission: author
     * return: id, content, time
     */
    @PostMapping(path = "/modify")
    @Authorization
    public ResponseEntity<ModifyReplyContentResponse> modifyReplyContent(@CurrentUser UserEntity user,
                                                                         @RequestBody ModifyReplyContentRequest request){
        /* invalid id error */
        Optional<BbsReplyEntity> ret = bbsReplyRepository.findById(request.getId());
        if(!ret.isPresent())
            return new ResponseEntity<>(new ModifyReplyContentResponse("no such reply", -1, null, null), HttpStatus.BAD_REQUEST);
        BbsReplyEntity reply = ret.get();

        /* permission error */
        if(!user.getUid().equals(reply.getAuthor().getUid()))
            return new ResponseEntity<>(new ModifyReplyContentResponse("permission error", -1, null, null), HttpStatus.FORBIDDEN);

        reply.setContent(request.getNewContent());

        Date time = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mediumDateFormat.format(time);
        reply.setTime(time);

        bbsReplyRepository.save(reply);
        return new ResponseEntity<>(new ModifyReplyContentResponse("ok", reply.getId(), reply.getContent(), reply.getTime()), HttpStatus.OK);
    }


    /* show all information under a certain topic
     * request: topic id, pages to show(10 per page)
     * return: see doc
     * v1.0,
     */
    @PostMapping(path = "/info")
    @Authorization
    public ResponseEntity<GetAllReplyResponse> getAllReplyInfo(@CurrentUser UserEntity user,
                                                               @RequestBody GetAllReplyRequest request){


    }
}
