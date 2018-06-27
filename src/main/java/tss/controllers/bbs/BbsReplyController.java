package tss.controllers.bbs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsReplyEntity;
import tss.entities.bbs.BbsRetrieveEntity;
import tss.entities.bbs.BbsTopicEntity;
import tss.repositories.UserRepository;
import tss.repositories.bbs.BbsReplyRepository;
import tss.repositories.bbs.BbsRetrieveRepository;
import tss.repositories.bbs.BbsTopicRepository;
import tss.requests.information.bbs.*;
import tss.responses.information.bbs.*;

import java.text.DateFormat;
import java.util.*;

@Controller
@RequestMapping(path = "/reply")
public class BbsReplyController {
    private final BbsTopicRepository bbsTopicRepository;
    private final UserRepository userRepository;
    private final BbsReplyRepository bbsReplyRepository;
    private final BbsRetrieveRepository bbsRetrieveRepository;

    public BbsReplyController(BbsTopicRepository bbsTopicRepository, UserRepository userRepository, BbsReplyRepository bbsReplyRepository, BbsRetrieveRepository bbsRetrieveRepository) {
        this.bbsTopicRepository = bbsTopicRepository;
        this.userRepository = userRepository;
        this.bbsReplyRepository = bbsReplyRepository;
        this.bbsRetrieveRepository = bbsRetrieveRepository;
    }

    /**
     * create a new reply to a topic/reply
     * request: tid, text, quoteIndex
     * permission: user in the section?
     * return: status
     * v1.0, done
     */
    @PostMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddBbsReplyResponse> addReply(@CurrentUser UserEntity user,
                                                        @RequestBody AddBbsReplyRequest request) {
        /* permission error & invalid topic id error */
        long topicId = Long.valueOf(request.getTid());
        Optional<BbsTopicEntity> ret = bbsTopicRepository.findById(topicId);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new AddBbsReplyResponse("no such topic"), HttpStatus.BAD_REQUEST);
        }

        BbsTopicEntity topic = ret.get();

        BbsReplyEntity reply = new BbsReplyEntity();


        reply.setAuthor(user);
        reply.setBelongedTopic(topic);


        reply.setContent(request.getText());

        Date time = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mediumDateFormat.format(time);
        reply.setTime(time);

        /* set topic last reply time */
        reply.getBelongedTopic().setLastReplyTime(time);

        reply.setQuoteIndex(Integer.valueOf(request.getQuoteIndex()));
        reply.setIndex(topic.getReplyNum() + 1);

        String quoteIndex = request.getQuoteIndex();

        /* if quoted, the one quoted, unread ++
         * wait to be confirm
         */
        if (Integer.valueOf(quoteIndex) != 0) {
            /* do to the quoted one, unread ++ */
            BbsReplyEntity quoted = bbsReplyRepository.findByBelongedTopicAndIndex(topic, Integer.valueOf(quoteIndex));
            quoted.setUnread(quoted.getUnread() + 1);
            bbsReplyRepository.save(quoted);

            /* this reply unread */
            reply.setStatus(0);
        } else {
            reply.setStatus(-1);
        }

        /* the new reply has not been quoted */
        reply.setUnread(0);
        bbsReplyRepository.save(reply);

        /* need to add the reply number in the topic */
        topic.setReplyNum(topic.getReplyNum() + 1);

        bbsTopicRepository.save(topic);

        return new ResponseEntity<>(new AddBbsReplyResponse("add ok"), HttpStatus.OK);
    }


    /**
     * delete a reply by id
     * request: id
     * permission: author, manager
     * return: id, author name
     */
    @DeleteMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteBbsReplyResponse> deleteReply(@CurrentUser UserEntity user,
                                                              @RequestBody DeleteBbsReplyRequest request) {
        /* invalid id error */
        Optional<BbsReplyEntity> ret = bbsReplyRepository.findById(request.getId());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new DeleteBbsReplyResponse("no such reply", -1, null), HttpStatus.BAD_REQUEST);
        }
        BbsReplyEntity reply = ret.get();

        /* permission error */
        // TODO

        bbsReplyRepository.delete(reply);

        /* need to add the reply number in the topic */
        reply.getBelongedTopic().setReplyNum(reply.getBelongedTopic().getReplyNum() - 1);

        return new ResponseEntity<>(new DeleteBbsReplyResponse("ok", reply.getId(), reply.getAuthor().getName()), HttpStatus.OK);
    }


    /**
     * modify a reply content by id
     * request: id
     * permission: author
     * return: id, content, time
     */
    @PostMapping(path = "/modify")
    @Authorization
    public ResponseEntity<ModifyReplyContentResponse> modifyReplyContent(@CurrentUser UserEntity user,
                                                                         @RequestBody ModifyReplyContentRequest request) {
        /* invalid id error */
        Optional<BbsReplyEntity> ret = bbsReplyRepository.findById(request.getId());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyReplyContentResponse("no such reply", -1, null, null), HttpStatus.BAD_REQUEST);
        }
        BbsReplyEntity reply = ret.get();

        /* permission error */
        if (!user.getUid().equals(reply.getAuthor().getUid())) {
            return new ResponseEntity<>(new ModifyReplyContentResponse("permission error", -1, null, null), HttpStatus.FORBIDDEN);
        }

        reply.setContent(request.getNewContent());

        Date time = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mediumDateFormat.format(time);
        reply.setTime(time);

        bbsReplyRepository.save(reply);
        return new ResponseEntity<>(new ModifyReplyContentResponse("ok", reply.getId(), reply.getContent(), reply.getTime()), HttpStatus.OK);
    }


    /**
     * show all information under a certain topic
     * request: topic id, pages to show(10 per page)
     * return: see doc
     * v1.0, done
     */
    @PostMapping(path = "/info")
    public ResponseEntity<GetAllReplyResponse> getAllReplyInfo(@RequestBody GetAllReplyRequest request) {
        Optional<BbsTopicEntity> ret = bbsTopicRepository.findById(Long.valueOf(request.getTid()));
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetAllReplyResponse(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
        }

        BbsTopicEntity topic = ret.get();

        String title = topic.getName();
        String totalPage = String.valueOf(topic.getReplyNum() / 10 + 1);
        String currentPage = request.getPage().toString();
        String postTime = topic.getTime().toString();
        String boardName = topic.getBelongedSection().getName();
        String boardID = String.valueOf(topic.getBelongedSection().getId());
        String topicID = String.valueOf(topic.getId());

        /* lz information */
        String lzid = topic.getAuthor().getUid();
        String lztext = topic.getContent();
        String lzphoto = topic.getAuthor().getPhoto();
        String lztime = topic.getTime().toString();
        String lzname = topic.getAuthor().getName();


        List<String> ids = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        List<String> quotes = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> photos = new ArrayList<>();
        List<String> indexs = new ArrayList<>();
        List<String> quoteAuthors = new ArrayList<>();
        List<String> quoteTimes = new ArrayList<>();
        List<String> quoteIndexs = new ArrayList<>();
        List<String> names = new ArrayList<>();


        /* all replied under the certain topic */
        String page = request.getPage();
        int index = (Integer.valueOf(page) - 1) * 10 + 1;
        for (; index <= Integer.valueOf(page) * 10 && index <= topic.getReplyNum(); index++) {

            /* current index information */
            BbsReplyEntity reply = bbsReplyRepository.findByBelongedTopicAndIndex(topic, index);
            UserEntity user = reply.getAuthor();
            ids.add(String.valueOf(reply.getId()));
            texts.add(reply.getContent());
            times.add(reply.getTime().toString());
            indexs.add(String.valueOf(index));

            /* current user information */
            photos.add(user.getPhoto());
            names.add(user.getName());

            /* quoted reply information */
            Integer quoteIndex = reply.getQuoteIndex();
            /* no quote */
            if (quoteIndex == 0) {
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
        return new ResponseEntity<>(new GetAllReplyResponse(title, totalPage, currentPage, postTime, boardName, boardID, topicID, lzid, lztext, lzphoto, lztime, lzname, ids, texts, quotes, times, photos, indexs, quoteAuthors, quoteTimes, quoteIndexs, names), HttpStatus.OK);
    }


    /**
     * confirm a reply to be read
     * - the reply itself, change the status
     * - the quoted one, decrease the unread
     * v1.0, done
     */
    @PostMapping(path = "/confirm")
    @Authorization
    public ResponseEntity<ConfirmReplyReadResponse> confirmReplyRead(@CurrentUser UserEntity user,
                                                                     @RequestBody ConfirmReplyReadRequest request) {
        Integer index = Integer.valueOf(request.getReplyPos());
        BbsTopicEntity topic = bbsTopicRepository.findById(Long.valueOf(request.getTopicID())).get();

        BbsReplyEntity reply = bbsReplyRepository.findByBelongedTopicAndIndex(topic, index);
        reply.setStatus(1);

        BbsReplyEntity quoted = bbsReplyRepository.findByBelongedTopicAndIndex(topic, reply.getQuoteIndex());
        quoted.setUnread(quoted.getUnread() - 1);
        bbsReplyRepository.save(quoted);
        bbsReplyRepository.save(reply);

        return new ResponseEntity<>(new ConfirmReplyReadResponse("confirm ok!"), HttpStatus.OK);
    }


    /**
     * show unread, message & replies
     * v1.0, done
     */
    @GetMapping(path = "/unread")
    @Authorization
    public ResponseEntity<CountUnreadResponse> countUnread(@CurrentUser UserEntity user) {
        Integer unMeg = 0;
        Integer unReply = 0;

        /* cannot be null */
        List<BbsRetrieveEntity> megs = bbsRetrieveRepository.findByReceiver(user);
        for (BbsRetrieveEntity meg : megs) {
            if (!meg.getIsChecked()) {
                unMeg++;
            }
        }

        Optional<List<BbsReplyEntity>> ret = bbsReplyRepository.findByAuthor(user);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new CountUnreadResponse(null, null), HttpStatus.BAD_REQUEST);
        }

        List<BbsReplyEntity> replies = ret.get();
        for (BbsReplyEntity reply : replies) {
            unReply += reply.getUnread();
        }

        return new ResponseEntity<>(new CountUnreadResponse(unMeg.toString(), unReply.toString()), HttpStatus.OK);
    }


    @PostMapping(path = "/show")
    @Authorization
    public ResponseEntity<ShowReplytoMeResponse> showReplytoMe(@CurrentUser UserEntity user,
                                                               @RequestBody ShowReplytoMeRequest request) {
        String currentPage = request.getPage();
        String totalPage;
        List<String> times = new ArrayList<>();
        List<String> userIDs = new ArrayList<>();
        List<String> userNames = new ArrayList<>();
        List<String> topicIDs = new ArrayList<>();
        List<String> replyPos = new ArrayList<>();
        List<String> reads = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        Optional<List<BbsReplyEntity>> ret = bbsReplyRepository.findByAuthor(user);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ShowReplytoMeResponse(null, null, null, null, null, null, null, null, null), HttpStatus.BAD_REQUEST);
        }

        List<BbsReplyEntity> replsAsAuthor = ret.get();

        int count = 0;
        /* search all reply as author */
        for (BbsReplyEntity replyAsAuthor : replsAsAuthor) {
            BbsTopicEntity topic = replyAsAuthor.getBelongedTopic();
            Integer index = replyAsAuthor.getIndex();

            /* search current topic */
            for (BbsReplyEntity reply : topic.getReplies()) {
                /* quote the reply */
                if (reply.getQuoteIndex().equals(index)) {
                    count++;
                    if (count < (Integer.valueOf(currentPage) - 1) * 10 + 1) {
                        continue;
                    }
                    if (count > Integer.valueOf(currentPage) * 10) {
                        continue;
                    }
                    times.add(reply.getTime().toString());
                    userIDs.add(reply.getAuthor().getUid());
                    userNames.add(reply.getAuthor().getName());
                    topicIDs.add(String.valueOf(reply.getBelongedTopic().getId()));
                    replyPos.add(reply.getIndex().toString());
                    reads.add(reply.getStatus().toString());
                    titles.add(reply.getBelongedTopic().getName());
                }
            }
        }
        totalPage = String.valueOf(count / 20 + 1);

        return new ResponseEntity<>(new ShowReplytoMeResponse(currentPage, totalPage, times, userIDs, userNames, topicIDs, replyPos, reads, titles), HttpStatus.OK);
    }
}
