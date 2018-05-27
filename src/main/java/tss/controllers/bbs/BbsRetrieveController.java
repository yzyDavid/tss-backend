package tss.controllers.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.UserEntity;
import tss.entities.bbs.BbsRetrieveEntity;
import tss.repositories.UserRepository;
import tss.repositories.bbs.BbsRetrieveRepository;
import tss.requests.information.bbs.AddBbsRetrieveRequest;
import tss.requests.information.bbs.ReadBbsRetrieveRequest;
import tss.responses.information.bbs.AddBbsRetrieveResponse;
import tss.responses.information.bbs.CheckRetrieveResponse;
import tss.responses.information.bbs.ReadBbsRetrieveResponse;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/retrieve")
public class BbsRetrieveController {
    private UserRepository userRepository;
    private BbsRetrieveRepository bbsRetrieveRepository;

    @Autowired
    public BbsRetrieveController(UserRepository userRepository, BbsRetrieveRepository bbsRetrieveRepository) {
        this.userRepository = userRepository;
        this.bbsRetrieveRepository = bbsRetrieveRepository;
    }

    /* create a new message, mark as unchecked
     * request: id, r-id, content
     * permission: anyone login
     * return: id, s-id, r-id, content, time
     */
    @PostMapping(path = "/create")
    @Authorization
    public ResponseEntity<AddBbsRetrieveResponse> sendRetrieve(@CurrentUser UserEntity user,
                                                               @RequestBody AddBbsRetrieveRequest request) {
        /* message id exists */
        long id = request.getId();
        Optional<BbsRetrieveEntity> ret = bbsRetrieveRepository.findById(id);
        if (ret.isPresent()) {
            return new ResponseEntity<>(new AddBbsRetrieveResponse("duplicated message", -1, null, null, null, null), HttpStatus.BAD_REQUEST);
        }

        /* receiver id invalid */
        long receiverId = request.getReceiverdId();
        String content = request.getContent();
        Optional<UserEntity> retu = userRepository.findById(String.valueOf(receiverId));
        if (!retu.isPresent()) {
            return new ResponseEntity<>(new AddBbsRetrieveResponse("no receriver id", -1, null, null, null, null), HttpStatus.BAD_REQUEST);
        }

        UserEntity sender = user;
        UserEntity receiver = retu.get();

        BbsRetrieveEntity retrieve = new BbsRetrieveEntity(sender, receiver);
        retrieve.setId(id);
        retrieve.setContent(content);
        retrieve.setChecked(false);

        Date time = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mediumDateFormat.format(time);

        retrieve.setTime(time);
        bbsRetrieveRepository.save(retrieve);

        return new ResponseEntity<>(new AddBbsRetrieveResponse("ok", id, sender.getUid(), receiver.getUid(), content, time), HttpStatus.OK);
    }


    /* check current user's retrieves information, not read
     * request: null
     * permission: login
     * return: L - ids, L - contents, L - times
     */
    @GetMapping(path = "/check")
    @Authorization
    public ResponseEntity<CheckRetrieveResponse> checkUserRetrieve(@CurrentUser UserEntity user) {
        List<Long> ids = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        List<Date> times = new ArrayList<>();

        List<BbsRetrieveEntity> messages = bbsRetrieveRepository.findByReceiver(user);
        for (BbsRetrieveEntity mes : messages) {
            if (mes.isChecked()) {
                continue;
            }
            ids.add(mes.getId());
            contents.add(mes.getContent());

            Date time = mes.getTime();
            DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
            mediumDateFormat.format(time);
            times.add(time);
        }

        if (ids.isEmpty()) {
            return new ResponseEntity<>(new CheckRetrieveResponse("no message", null, null, null), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new CheckRetrieveResponse("ok", ids, contents, times), HttpStatus.OK);
    }

    /* read a message with certain id
     * request body: id
     * permission: mes to him
     * return: id, s-id, r-id, content, time
     */
    @PostMapping(path = "/read")
    @Authorization
    public ResponseEntity<ReadBbsRetrieveResponse> readRetrieveById(@CurrentUser UserEntity user,
                                                                    @RequestBody ReadBbsRetrieveRequest request) {
        /* invalid id */
        long id = request.getId();
        Optional<BbsRetrieveEntity> ret = bbsRetrieveRepository.findById(id);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ReadBbsRetrieveResponse("no such mesg", -1, null, null, null, null), HttpStatus.BAD_REQUEST);
        }

        BbsRetrieveEntity mesg = ret.get();
        /* permission denied */
        if (!user.getUid().equals(mesg.getReceiver().getUid())) {
            return new ResponseEntity<>(new ReadBbsRetrieveResponse("permission denied", -1, null, null, null, null), HttpStatus.FORBIDDEN);
        }

        mesg.setChecked(true);

        return new ResponseEntity<>(new ReadBbsRetrieveResponse("ok", id, mesg.getSender().getUid(),
                mesg.getReceiver().getUid(), mesg.getContent(), mesg.getTime()), HttpStatus.OK);
    }

}
