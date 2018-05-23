package tss.controllers.bbs;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.rmi.log.LogInputStream;
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
import java.util.*;

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

    /* create a new reply to a topic/reply
     * request: tid, text, quoteIndex
     * permission: user in the section?
     * return: status
     */
    @PostMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddBbsReplyResponse> addReply(@CurrentUser UserEntity user,
                                                        @RequestBody AddBbsReplyRequest request){
        /* permission error & invalid topic id error */
        long topicId = request.getTid();
        Optional<BbsTopicEntity> ret = bbsTopicRepository.findById(topicId);
        if(!ret.isPresent())
            return new ResponseEntity<>(new AddBbsReplyResponse("no such topic"), HttpStatus.BAD_REQUEST);

        BbsTopicEntity topic = ret.get();

//        BbsSectionEntity section = topic.getBelongedSection();
//        TeachesEntity teaches = section.getTeaches();

//        /* list user's teaches, check permission */
//        boolean permission = false;
//        Set<TeachesEntity> userTeaches = user.getTeaches();
//        for(TeachesEntity t : userTeaches){
//            if(t.getId() == teaches.getId()){
//                permission = true;
//                break;
//            }
//        }
//
//        if(!permission)
//            return new ResponseEntity<>(new AddBbsReplyResponse("permission error"), HttpStatus.FORBIDDEN);

        BbsReplyEntity reply = new BbsReplyEntity(user, topic);

        reply.setContent(request.getText());

        Date time = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mediumDateFormat.format(time);
        reply.setTime(time);

        reply.setQuoteIndex(request.getQuoteIndex());
        reply.setIndex(topic.getReplyNum()+1);

        bbsReplyRepository.save(reply);

        /* need to add the reply number in the topic */
        topic.setReplyNum(topic.getReplyNum()+1);

        return new ResponseEntity<>(new AddBbsReplyResponse("add ok"), HttpStatus.OK);
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
     * v1.0, done
     */
    @PostMapping(path = "/info")
    @Authorization
    public ResponseEntity<GetAllReplyResponse> getAllReplyInfo(@CurrentUser UserEntity user,
                                                               @RequestBody GetAllReplyRequest request){
        Optional<BbsTopicEntity> ret = bbsTopicRepository.findById(request.getTid());
        if(!ret.isPresent())
            return new ResponseEntity<>(new GetAllReplyResponse(null, null,null,null,null,null,null,null,null,null,null,null,null,null,null,null), HttpStatus.BAD_REQUEST);

        BbsTopicEntity topic = ret.get();

        String title = topic.getName();
        String totalPage = String.valueOf(topic.getReplyNum()/10);
        String currentPage = request.getPageNum().toString();
        String postTime = topic.getTime().toString();
        String boardName = topic.getBelongedSection().getName();
        String boardID = String.valueOf(topic.getBelongedSection().getId());
        String topicID = String.valueOf(topic.getId());

        List<String> ids = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        List<String> quotes = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> photos = new ArrayList<>();
        List<String> indexs = new ArrayList<>();
        List<String> quoteAuthors = new ArrayList<>();
        List<String> quoteTimes = new ArrayList<>();
        List<String> quoteIndexs = new ArrayList<>();

        /* all replied under the certain topic */
        int index = (request.getPageNum()-1)*10 + 1;
        for( ; index <= request.getPageNum()*10 && index <= topic.getReplyNum(); index++){

            /* current index information */
            BbsReplyEntity reply = bbsReplyRepository.findByBelongedTopicAndIndex(topic, index);
            ids.add(String.valueOf(reply.getId()));
            texts.add(reply.getContent());
            times.add(reply.getTime().toString());
            indexs.add(String.valueOf(index));

            /* current user information */
            photos.add(user.getPhoto());

            /* quoted reply information */
            Integer quoteIndex = reply.getQuoteIndex();
            /* no quote */
            if(quoteIndex == 0){
                quotes.add("");
                quoteAuthors.add("");
                quoteTimes.add("");
                quoteIndexs.add("0");
                continue;
            }
            BbsReplyEntity quoteReply = bbsReplyRepository.findByBelongedTopicAndIndex(topic, quoteIndex);
            quotes.add(quoteReply.getContent());
            quoteAuthors.add(quoteReply.getAuthor().getName());
            quoteTimes.add(quoteReply.getTime().toString());
            quoteIndexs.add(quoteReply.getIndex().toString());
        }

        if(ids.isEmpty())
            return new ResponseEntity<>(new GetAllReplyResponse(null, null,null,null,null,null,null,null,null,null,null,null,null,null,null,null), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new GetAllReplyResponse(title,totalPage,currentPage,postTime,boardName,boardID,topicID,ids,texts,quotes,times,photos,indexs,quoteAuthors,quoteTimes,quoteIndexs), HttpStatus.BAD_REQUEST);
    }
}
