package tss.controllers.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsSectionEntity;
import tss.entities.bbs.BbsTopicEntity;
import tss.repositories.UserRepository;
import tss.repositories.bbs.BbsSectionRepository;
import tss.repositories.bbs.BbsTopicRepository;
import tss.requests.information.bbs.AddBbsTopicRequest;
import tss.requests.information.bbs.DeleteBbsTopicRequest;
import tss.requests.information.bbs.ModifyTopicContentRequest;
import tss.responses.information.bbs.AddBbsTopicResponse;
import tss.responses.information.bbs.DeleteBbsTopicResponse;
import tss.responses.information.bbs.ModifyTopicContentResponse;

import java.text.DateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping(path = "/topic")
public class BbsTopicController {
    private final BbsSectionRepository bbsSectionRepository;
    private final UserRepository userRepository;
    private final BbsTopicRepository bbsTopicRepository;

    @Autowired
    public BbsTopicController(BbsSectionRepository bbsSectionRepository,
                              UserRepository userRepository,
                              BbsTopicRepository bbsTopicRepository){
        this.bbsSectionRepository = bbsSectionRepository;
        this.userRepository = userRepository;
        this.bbsTopicRepository = bbsTopicRepository;
    }

    /* create a topic
     * request: id, name, section id, content
     * permission: anyone login
     * return: id, name, content, time
     */
    @PostMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddBbsTopicResponse> addBbsTopic(@CurrentUser UserEntity user,
                                                           @RequestBody AddBbsTopicRequest request){
        /* every topic bind to a section
         * invalid section id error
         */
        long sectionId = request.getSectionId();
        Optional<BbsSectionEntity> ret = bbsSectionRepository.findById(sectionId);
        if(!ret.isPresent())
            return new ResponseEntity<>(new AddBbsTopicResponse("invalid section id", -1, null, null, null), HttpStatus.BAD_REQUEST);

        /* anyone login got the permission
         * bind the author & belongedSectionId
         */
        BbsTopicEntity topic = new BbsTopicEntity(user, ret.get());

        /* init id & name & content */
        long topicId = request.getId();
        String name = request.getName();
        String content = request.getContent();
        topic.setContent(content);
        topic.setName(name);
        topic.setId(topicId);

        /* init timestamp */
        Date date = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mediumDateFormat.format(date);
        topic.setTime(date);

        /* reply num */
        bbsTopicRepository.save(topic);
        return new ResponseEntity<>(new AddBbsTopicResponse("ok", topicId, name, content, date), HttpStatus.OK);
    }

    /* delete by id
     * request: id
     * permission : manager, author
     * response: id, name, author name;
     */
    @DeleteMapping(path = "delete")
    @Authorization
    public ResponseEntity<DeleteBbsTopicResponse> deleteBbsTopic(@CurrentUser UserEntity user,
                                                                 @RequestBody DeleteBbsTopicRequest request){
        /* invalid topic id error */
        Optional<BbsTopicEntity> ret = bbsTopicRepository.findById(request.getId());
        if(!ret.isPresent())
            return new ResponseEntity<>(new DeleteBbsTopicResponse("no such topic id", -1, null, null), HttpStatus.BAD_REQUEST);

        BbsTopicEntity topic = ret.get();

        /* only author and manager get the permission */
        if(user.getType() != UserEntity.TYPE_MANAGER
                || !user.getUid().equals(topic.getAuthor().getUid()))
            return new ResponseEntity<>(new DeleteBbsTopicResponse("permission denied", -1, null, null), HttpStatus.FORBIDDEN);

        String authorName = topic.getAuthor().getName();
        String topicName = topic.getName();
        bbsTopicRepository.delete(topic);

        return new ResponseEntity<>(new DeleteBbsTopicResponse("ok", request.getId(), topicName, authorName), HttpStatus.OK);
    }

    /* modify the topic content
     * request: id, new content
     * permission: author only
     * return: id, name, content, time
     */
    @PostMapping(path = "modify")
    @Authorization
    public ResponseEntity<ModifyTopicContentResponse> modifyTopicContent(@CurrentUser UserEntity user,
                                                                         @RequestBody ModifyTopicContentRequest request){
        /* invalid topic id error */
        Optional<BbsTopicEntity> ret = bbsTopicRepository.findById(request.getId());
        if(!ret.isPresent())
            return new ResponseEntity<>(new ModifyTopicContentResponse("no such topic", -1, null, null, null), HttpStatus.BAD_REQUEST);
        BbsTopicEntity topic = ret.get();

        /* not author, error */
        if(!user.getUid().equals(topic.getAuthor().getUid()))
            return new ResponseEntity<>(new ModifyTopicContentResponse("permission denied", -1, null, null, null), HttpStatus.FORBIDDEN);

        /* modify content by new content */
        topic.setContent(request.getNewContent());

        Date date = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mediumDateFormat.format(date);
        topic.setTime(date);

        bbsTopicRepository.save(topic);
        return new ResponseEntity<>(new ModifyTopicContentResponse("ok", topic.getId(), topic.getName(), topic.getContent(), topic.getTime()), HttpStatus.OK);
    }


}
