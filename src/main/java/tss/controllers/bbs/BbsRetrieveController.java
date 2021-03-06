package tss.controllers.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.util.resources.ga.LocaleNames_ga;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsRetrieveEntity;
import tss.repositories.UserRepository;
import tss.repositories.bbs.BbsRetrieveRepository;
import tss.requests.information.bbs.AddBbsRetrieveRequest;
import tss.requests.information.bbs.CheckInBoxRequest;
import tss.requests.information.bbs.CheckOutBoxRequest;
import tss.requests.information.bbs.ReadBbsRetrieveRequest;
import tss.responses.information.bbs.AddBbsRetrieveResponse;
import tss.responses.information.bbs.CheckInBoxResponse;
import tss.responses.information.bbs.CheckOutBoxResponse;
import tss.responses.information.bbs.ReadBbsRetrieveResponse;

import java.text.DateFormat;
import java.util.*;

@Controller
@RequestMapping(path = "/imessage")
public class BbsRetrieveController {
    private UserRepository userRepository;
    private BbsRetrieveRepository bbsRetrieveRepository;

    @Autowired
    public BbsRetrieveController(UserRepository userRepository, BbsRetrieveRepository bbsRetrieveRepository) {
        this.userRepository = userRepository;
        this.bbsRetrieveRepository = bbsRetrieveRepository;
    }

    /**
     * create a new message, mark as unchecked
     * request: id, r-id, content
     * permission: anyone login
     * return: id, s-id, r-id, content, time
     * v1.0, done
     */
    @PostMapping(path = "/send")
    @Authorization
    public ResponseEntity<AddBbsRetrieveResponse> sendRetrieve(@CurrentUser UserEntity user,
                                                               @RequestBody AddBbsRetrieveRequest request) {
        UserEntity sender = user;

        Optional<UserEntity> retd = userRepository.findById(request.getDestination());
        if (!retd.isPresent()) {
            return new ResponseEntity<>(new AddBbsRetrieveResponse("send failed, no such user!"), HttpStatus.BAD_REQUEST);
        }

        UserEntity receiver = retd.get();


        BbsRetrieveEntity retrieve = new BbsRetrieveEntity();
        retrieve.setReceiver(receiver);
        retrieve.setSender(sender);

        retrieve.setTitle(request.getTitle());
        retrieve.setContent(request.getText());
        retrieve.setIsChecked(false);

        Date time = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mediumDateFormat.format(time);

        retrieve.setTime(time);
        bbsRetrieveRepository.save(retrieve);

        return new ResponseEntity<>(new AddBbsRetrieveResponse("send ok!"), HttpStatus.OK);
    }


    /**
     * check current user's inbox
     * request: page
     * permission: login
     * return: see doc
     * v1.0, done
     */
    @PostMapping(path = "/inbox")
    @Authorization
    public ResponseEntity<CheckInBoxResponse> checkInBox(@CurrentUser UserEntity user,
                                                         @RequestBody CheckInBoxRequest request) {
        String currentPage = request.getPage();


        List<BbsRetrieveEntity> messages = bbsRetrieveRepository.findByReceiver(user);

        String totalPage = String.valueOf(messages.size() / 20 + 1);

        List<String> destinations = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> reads = new ArrayList<>();
        List<String> userIDs = new ArrayList<>();
        List<String> letterIDs = new ArrayList<>();

        int count = 0;
        for (BbsRetrieveEntity mess : messages) {
            count++;
            if (count < (Integer.valueOf(currentPage) - 1) * 10 + 1) {
                continue;
            }
            if (count > Integer.valueOf(currentPage) * 10) {
                break;
            }
            destinations.add(mess.getSender().getName());
            titles.add(mess.getTitle());
            texts.add(mess.getContent());
            times.add(mess.getTime().toString());
            reads.add(String.valueOf(mess.getIsChecked()));
            userIDs.add(mess.getSender().getUid().toString());
            letterIDs.add(String.valueOf(mess.getId()));
        }

        return new ResponseEntity<>(new CheckInBoxResponse(currentPage, totalPage, destinations, titles, texts, times, reads, userIDs, letterIDs), HttpStatus.OK);
    }


    /**
     * check current user's outbox
     * request: page
     * permission: login
     * return: see doc
     * v1.0, done
     */
    @PostMapping(path = "/outbox")
    @Authorization
    public ResponseEntity<CheckOutBoxResponse> checkOutBox(@CurrentUser UserEntity user,
                                                           @RequestBody CheckOutBoxRequest request) {
        String currentPage = request.getPage();

        List<BbsRetrieveEntity> messages = bbsRetrieveRepository.findBySender(user);

        String totalPage = String.valueOf(messages.size() / 20 + 1);

        List<String> destinations = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> userIDs = new ArrayList<>();


        int count = 0;
        for (BbsRetrieveEntity mess : messages) {
            count++;
            if (count < (Integer.valueOf(currentPage) - 1) * 10 + 1) {
                continue;
            }
            if (count > Integer.valueOf(currentPage) * 10) {
                break;
            }
            destinations.add(mess.getReceiver().getName());
            titles.add(mess.getTitle());
            texts.add(mess.getContent());
            times.add(mess.getTime().toString());
            userIDs.add(mess.getReceiver().getUid());
        }

        return new ResponseEntity<>(new CheckOutBoxResponse(currentPage, totalPage, destinations, titles, texts, times, userIDs), HttpStatus.OK);
    }


    /**
     * confirm a message to be read
     * with certain id
     * v1.0, done
     */
    @PostMapping(path = "/read")
    @Authorization
    public ResponseEntity<ReadBbsRetrieveResponse> readRetrieveById(@CurrentUser UserEntity user,
                                                                    @RequestBody ReadBbsRetrieveRequest request) {
        /* invalid id */
        Optional<BbsRetrieveEntity> ret = bbsRetrieveRepository.findById(Long.valueOf(request.getLetterId()));
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ReadBbsRetrieveResponse("no such mesg"), HttpStatus.BAD_REQUEST);
        }

        BbsRetrieveEntity msg = ret.get();

        msg.setIsChecked(true);

        bbsRetrieveRepository.save(msg);

        return new ResponseEntity<>(new ReadBbsRetrieveResponse("confirm succuess!"), HttpStatus.OK);
    }


    //@PostMapping(path = "")
}
