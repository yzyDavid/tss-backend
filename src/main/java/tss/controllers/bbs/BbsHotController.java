package tss.controllers.bbs;

import org.hibernate.annotations.GeneratorType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.swing.SwingUtilities2;
import tss.entities.bbs.BbsSectionEntity;
import tss.repositories.UserRepository;
import tss.repositories.bbs.BbsReplyRepository;
import tss.repositories.bbs.BbsSectionRepository;
import tss.repositories.bbs.BbsTopicRepository;
import tss.responses.information.bbs.ListHotResponse;

import javax.xml.ws.Response;

@Controller
@RequestMapping(path = "/hot")
public class BbsHotController {
    private final UserRepository userRepository;
    private final BbsSectionRepository sectionRepository;
    private final BbsTopicRepository topicRepository;
    private final BbsReplyRepository replyRepository;

    public BbsHotController(UserRepository userRepository, BbsSectionRepository sectionRepository, BbsTopicRepository topicRepository, BbsReplyRepository replyRepository) {
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.topicRepository = topicRepository;
        this.replyRepository = replyRepository;
    }

//    @GetMapping(path = "/topic")
//    public Response<ListHotResponse> showHot(){
//
//    }

//    @GetMapping(path = "hnum")
//
//}    public Response<ListHotResponse> listHourHot(){
//
//
//    @GetMapping(path = "wnum")
//    public Response<ListHotResponse> listWeekHot(){
//
//    }
//
//    @GetMapping(path = "mnum")
//    public Response<ListHotResponse> lisMonthHot(){
//
//    }
}
