package tss.controllers.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.configs.Config;
import tss.controllers.UserController;
import tss.entities.TypeGroupEntity;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsSectionEntity;
import tss.entities.bbs.BbsTopicEntity;
import tss.repositories.UserRepository;
import tss.repositories.bbs.BbsSectionRepository;
import tss.repositories.bbs.BbsTopicRepository;
import tss.requests.information.bbs.*;
import tss.responses.information.bbs.*;

import java.text.DateFormat;
import java.util.*;

@Controller
@RequestMapping(path = "/topic")
public class BbsTopicController {
    private final BbsSectionRepository bbsSectionRepository;
    private final UserRepository userRepository;
    private final BbsTopicRepository bbsTopicRepository;

    @Autowired
    public BbsTopicController(BbsSectionRepository bbsSectionRepository,
                              UserRepository userRepository,
                              BbsTopicRepository bbsTopicRepository) {
        this.bbsSectionRepository = bbsSectionRepository;
        this.userRepository = userRepository;
        this.bbsTopicRepository = bbsTopicRepository;
    }

    /**
     * before add, return the boardName
     * v1.0, done
     */
    @PostMapping(path = "/beforeadd")
    public ResponseEntity<BeforeAddResponse> beforeAdd(@RequestBody BeforeAddRequest request) {
        Optional<BbsSectionEntity> ret = bbsSectionRepository.findById(Long.valueOf(request.getBoardID()));
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new BeforeAddResponse(null), HttpStatus.OK);
        }
        BbsSectionEntity section = ret.get();
        return new ResponseEntity<>(new BeforeAddResponse(section.getName()), HttpStatus.OK);
    }

    /**
     * create a topic
     * request: id, name, section id, content
     * permission: anyone login
     * return: id, name, content, time
     * v1.0, done
     * FIXME
     */
    @PostMapping(path = "/add")
    public ResponseEntity<AddBbsTopicResponse> addBbsTopic(//@CurrentUser UserEntity user,
                                                           @RequestBody AddBbsTopicRequest request) {
        /*
         * invalid section id error
         */
        long sectionId = Long.valueOf(request.getBoardID());
        Optional<BbsSectionEntity> ret = bbsSectionRepository.findById(sectionId);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new AddBbsTopicResponse("-1"), HttpStatus.BAD_REQUEST);
        }

        BbsTopicEntity topic = new BbsTopicEntity();

        /* FIXME */
        UserEntity user = userRepository.findById("3150103333").get();


        topic.setAuthor(user);
        topic.setBelongedSection(ret.get());

        /* init id & name & content */
        String name = request.getTitle();
        String content = request.getText();
        topic.setContent(content);
        topic.setName(name);


        /* init timestamp, last reply time with create time */
        Date date = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mediumDateFormat.format(date);
        topic.setTime(date);
        topic.setLastReplyTime(date);

        /* id init auto */
        bbsTopicRepository.save(topic);

        return new ResponseEntity<>(new AddBbsTopicResponse(String.valueOf(topic.getId())), HttpStatus.OK);
    }

    /**
     * delete by id
     * request: id
     * permission : manager, author
     * response: status
     * v1.0, done
     * TODO check permission
     */
    @PostMapping(path = "/delete")
    //@Authorization
    public ResponseEntity<DeleteBbsTopicResponse> deleteBbsTopic(//@CurrentUser UserEntity user,
                                                                 @RequestBody DeleteBbsTopicRequest request) {
        /* invalid topic id error */
        Optional<BbsTopicEntity> ret = bbsTopicRepository.findById(Long.valueOf(request.getTopicID()));
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new DeleteBbsTopicResponse("no such id"), HttpStatus.BAD_REQUEST);
        }

        BbsTopicEntity topic = ret.get();

        /* only author and manager get the permission */
//        if (!Config.TYPES[1].equals(user.readTypeName())
//                && !user.getUid().equals(topic.getAuthor().getUid())) {
//            return new ResponseEntity<>(new DeleteBbsTopicResponse("permission denied"), HttpStatus.BAD_REQUEST);
//        }

        bbsTopicRepository.delete(topic);

        return new ResponseEntity<>(new DeleteBbsTopicResponse("delete ok"), HttpStatus.OK);
    }

    /**
     * modify the topic content
     * request: id, new content
     * permission: author only
     * return: id, name, content, time
     */
    @PostMapping(path = "modify")
    @Authorization
    public ResponseEntity<ModifyTopicContentResponse> modifyTopicContent(@CurrentUser UserEntity user,
                                                                         @RequestBody ModifyTopicContentRequest request) {
        /* invalid topic id error */
        Optional<BbsTopicEntity> ret = bbsTopicRepository.findById(request.getId());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyTopicContentResponse("no such topic", -1, null, null, null), HttpStatus.BAD_REQUEST);
        }
        BbsTopicEntity topic = ret.get();

        /* not author, error */
        if (!user.getUid().equals(topic.getAuthor().getUid())) {
            return new ResponseEntity<>(new ModifyTopicContentResponse("permission denied", -1, null, null, null), HttpStatus.FORBIDDEN);
        }

        /* modify content by new content */
        topic.setContent(request.getNewContent());

        Date date = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mediumDateFormat.format(date);
        topic.setTime(date);

        bbsTopicRepository.save(topic);
        return new ResponseEntity<>(new ModifyTopicContentResponse("ok", topic.getId(), topic.getName(), topic.getContent(), topic.getTime()), HttpStatus.OK);
    }


    /**
     * published topics by current user
     * v1.0, done
     */
    @GetMapping(path = "/published")
    public ResponseEntity<GetUserPublishedResponse> getUserPublished(//@CurrentUser UserEntity user,
                                                                     @RequestParam String page) {

        UserEntity user = userRepository.findById("3150102242").get();
        Optional<List<BbsTopicEntity>> ret = bbsTopicRepository.findByAuthor(user);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetUserPublishedResponse(null, null, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
        }

        List<BbsTopicEntity> topics = ret.get();

        String userName = user.getName();
        String currentPage = page;
        /* page + 1 */
        String totalPage = String.valueOf(topics.size() / 20 + 1);
        List<String> titles = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> topicIDs = new ArrayList<>();
        List<String> boardIDs = new ArrayList<>();
        List<String> boardNames = new ArrayList<>();

        Iterator<BbsTopicEntity> iter = topics.iterator();
        int count = 0;
        while (iter.hasNext()) {
            count++;
            if (count < (Integer.valueOf(page) - 1) * 20 + 1) {
                continue;
            }
            if (count > (Integer.valueOf(page) * 20)) {
                break;
            }

            BbsTopicEntity topic = iter.next();
            titles.add(topic.getName());
            topicIDs.add(String.valueOf(topic.getId()));
            boardNames.add(topic.getBelongedSection().getName());
            boardIDs.add(String.valueOf(topic.getBelongedSection().getId()));
            times.add(topic.getTime().toString());
        }

        return new ResponseEntity<>(new GetUserPublishedResponse(userName, titles, times, topicIDs, boardIDs, boardNames, currentPage, totalPage), HttpStatus.BAD_REQUEST);
    }


    /**
     * show all topics under a certain section
     * public part/ top part
     * v1.0, done
     */
    @PostMapping(path = "/topinfo")
    public ResponseEntity<GetAllTopicsPublicResponse> getAllTopTopics(@RequestBody GetAllTopicsPublicRequest request) {
        /* haven't deal with not found situation */
        Optional<BbsSectionEntity> sret = bbsSectionRepository.findById(Long.valueOf(request.getBoardID()));
        if (!sret.isPresent()) {
            return new ResponseEntity<>(new GetAllTopicsPublicResponse(null, null, null, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
        }

        BbsSectionEntity section = sret.get();

        String boardName = section.getName();
        String boardID = String.valueOf(section.getId());
        String boardText = section.getNotice();

        List<String> topTitles = new ArrayList<>();
        List<String> topAuthors = new ArrayList<>();
        List<String> topTimes = new ArrayList<>();
        List<String> topReplys = new ArrayList<>();
        List<String> topTopicIDs = new ArrayList<>();
        List<String> topLastReplyTimes = new ArrayList<>();

        Set<BbsTopicEntity> topics = section.getTopics();
        for (BbsTopicEntity topic : topics) {
            /* find topic been set top */
            if (topic.getIsTop()) {
                topTitles.add(topic.getName());
                topAuthors.add(topic.getAuthor().getName());
                topTimes.add(topic.getTime().toString());
                topReplys.add(String.valueOf(topic.getReplyNum()));
                topTopicIDs.add(String.valueOf(topic.getId()));

                topLastReplyTimes.add(topic.getLastReplyTime().toString());
            }
        }
        return new ResponseEntity<>(new GetAllTopicsPublicResponse(boardName, boardID, boardText, topTitles, topAuthors, topTimes, topReplys, topTopicIDs, topLastReplyTimes), HttpStatus.OK);
    }

    /**
     * show all topics under a certain section
     * page show, / not top part
     * v1.0, done
     */
    @PostMapping(path = "/info")
    public ResponseEntity<GetAllNotTopTopicsResponse> getAllNotTopTopics(//@CurrentUser UserEntity user,
                                                                         @RequestBody GetAllNotTopTopicsRequest request) {
        /* haven't deal with not found situation */
        Optional<BbsSectionEntity> sret = bbsSectionRepository.findById(Long.valueOf(request.getBoardID()));
        if (!sret.isPresent()) {
            return new ResponseEntity<>(new GetAllNotTopTopicsResponse(null, null, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
        }

        BbsSectionEntity section = sret.get();

        String currentPage = request.getCurrentPage();

        List<String> topicTitles = new ArrayList<>();
        List<String> topicAuthors = new ArrayList<>();
        List<String> topicTimes = new ArrayList<>();
        List<String> topicReplys = new ArrayList<>();
        List<String> topicIDs = new ArrayList<>();
        List<String> topicLastReplyTimes = new ArrayList<>();

        Set<BbsTopicEntity> topics = section.getTopics();
        String totalPage = String.valueOf(topics.size() / 20 + 1);

        Iterator<BbsTopicEntity> iter = topics.iterator();
        int count = 0;
        while (iter.hasNext()) {
            BbsTopicEntity topic = iter.next();
            if (topic.getIsTop()) {
                continue;
            }

            count++;
            if (count < (Integer.valueOf(currentPage) - 1) * 20 + 1) {
                continue;
            }
            if (count > (Integer.valueOf(currentPage) * 20)) {
                break;
            }

            /* show */
            topicTitles.add(topic.getName());
            topicAuthors.add(topic.getAuthor().getName());
            topicTimes.add(topic.getTime().toString());
            topicReplys.add(String.valueOf(topic.getReplyNum()));
            topicIDs.add(String.valueOf(topic.getId()));
            topicLastReplyTimes.add(topic.getLastReplyTime().toString());
        }

        return new ResponseEntity<>(new GetAllNotTopTopicsResponse(currentPage, totalPage, topicTitles, topicAuthors, topicTimes, topicReplys, topicIDs, topicLastReplyTimes), HttpStatus.OK);
    }


    /**
     * set a topic to be top
     * TODO need check permission
     * v1.0,
     */
    @PostMapping(path = "/settop")
    //@Authorization
    public ResponseEntity<SetTopicTopResponse> setTopicTop(//@CurrentUser UserEntity user,
                                                           @RequestBody SetTopicTopRequest request) {
        /* invalid topic id error */
        Optional<BbsTopicEntity> ret = bbsTopicRepository.findById(Long.valueOf(request.getTopicID()));
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new SetTopicTopResponse("no such topic"), HttpStatus.BAD_REQUEST);
        }

        BbsTopicEntity topic = ret.get();

//        if (!Config.TYPES[1].equals(user.readTypeName())) {
//            return new ResponseEntity<>(new SetTopicTopResponse("permission denied!"), HttpStatus.BAD_REQUEST);
//        }

        topic.setIsTop(true);
        bbsTopicRepository.save(topic);

        return new ResponseEntity<>(new SetTopicTopResponse("set top ok"), HttpStatus.OK);
    }


    @PostMapping(path = "/setntop")
    //@Authorization
    public ResponseEntity<SetTopicNotTopResponse> setTopicNotTop(//@CurrentUser UserEntity user,
                                                                 @RequestBody SetTopicNotTopRequest request) {
        /* invalid topic id error */
        Optional<BbsTopicEntity> ret = bbsTopicRepository.findById(Long.valueOf(request.getTopicID()));
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new SetTopicNotTopResponse("no such topic"), HttpStatus.BAD_REQUEST);
        }

        BbsTopicEntity topic = ret.get();

//        if (!Config.TYPES[1].equals(user.readTypeName())) {
//            return new ResponseEntity<>(new SetTopicTopResponse("permission denied!"), HttpStatus.BAD_REQUEST);
//        }

        if (topic.getIsTop()) {
            return new ResponseEntity<>(new SetTopicNotTopResponse("the topic is not top"), HttpStatus.BAD_REQUEST);
        }

        topic.setIsTop(false);
        bbsTopicRepository.save(topic);

        return new ResponseEntity<>(new SetTopicNotTopResponse("cancel top ok"), HttpStatus.OK);
    }
}
