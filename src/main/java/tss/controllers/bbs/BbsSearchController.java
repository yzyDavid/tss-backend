package tss.controllers.bbs;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

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
import tss.repositories.bbs.BbsReplyRepository;
import tss.repositories.bbs.BbsSectionRepository;
import tss.repositories.bbs.BbsTopicRepository;
import tss.requests.information.bbs.SearchInSectionRequest;
import tss.responses.information.bbs.SearchInSectionResponse;

import java.util.*;

/* search in a certain section */
@Controller
@RequestMapping(path = "/search")
public class BbsSearchController {

    private BbsSectionRepository bbsSectionRepository;
    private BbsTopicRepository bbsTopicRepository;
    private BbsReplyRepository bbsReplyRepository;

    @Autowired
    public BbsSearchController(BbsSectionRepository bbsSectionRepository, BbsTopicRepository bbsTopicRepository, BbsReplyRepository bbsReplyRepository) {
        this.bbsSectionRepository = bbsSectionRepository;
        this.bbsTopicRepository = bbsTopicRepository;
        this.bbsReplyRepository = bbsReplyRepository;
    }

    /* match String function */
    private static boolean contentMatch(String key, String content){
        int match = 0;
        String[] keys = key.split(" ");
        for(String k : keys){
            String[] words = content.split(" ");
            for(String w : words){
                if(w.charAt(w.length()-1) == ',' ||
                        w.charAt(w.length()-1) == '.')
                    w = w.substring(0, w.length()-1);

                /* w to match */
                if(w.equals(k))
                    match++;
            }
        }
        return match == keys.length;
    }







    /* search by content key words for topic
     * request: key page
     * permission: in the section?
     * return: see doc
     * v1.0, done
     */
    @PostMapping(path = "/topic")
    @Authorization
    public ResponseEntity<SearchInSectionResponse> searchInSection(@CurrentUser UserEntity user,
                                                                   @RequestBody SearchInSectionRequest request){
        String currentPage = request.getPage();
        String totalPage;

        List<String> titles = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> boardNames = new ArrayList<>();
        List<String> boardIDs = new ArrayList<>();
        List<String> topicIDs = new ArrayList<>();
        List<String> replyNums = new ArrayList<>();

        Iterator<BbsSectionEntity> iter = bbsSectionRepository.findAll().iterator();

        String key = request.getKey();

        int count = 0;
        while(iter.hasNext()){
            BbsSectionEntity section = iter.next();

            Set<BbsTopicEntity> topics = section.getTopics();
            for(BbsTopicEntity t : topics){
                /* search topic */
                /* topic content match */
                if(BbsSearchController.contentMatch(key, t.getContent())){
                    count++;
                    if(count < (Integer.valueOf(currentPage)-1)*10+1)
                        continue;
                    if(count > Integer.valueOf(currentPage)*10)
                        continue;

                    topicIDs.add(String.valueOf(t.getId()));
                    authors.add(t.getAuthor().getName());
                    titles.add(t.getName());
                    times.add(t.getTime().toString());
                    boardNames.add(t.getBelongedSection().getName());
                    boardIDs.add(String.valueOf(t.getBelongedSection().getId()));
                    replyNums.add(String.valueOf(t.getReplyNum()));
                }
            }
        }

        if(count%20 == 0)
            totalPage = String.valueOf(count/20);
        else
            totalPage = String.valueOf(count/20+1);

        return new ResponseEntity<>(new SearchInSectionResponse(currentPage,totalPage,titles,authors,times,boardNames,boardIDs,topicIDs,replyNums), HttpStatus.OK);
    }
}
