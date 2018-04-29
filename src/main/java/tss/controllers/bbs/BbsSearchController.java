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
    private static boolean contentMatch(String[] keys, String content){
        int match = 0;
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

    /* search by content key words
     * request: section-id, String[] keywords
     * permission: in the section
     * return: L-ids, L-authorsName, L-contents, L-times
     */
    @PostMapping(path = "/content")
    @Authorization
    public ResponseEntity<SearchInSectionResponse> searchInSection(@CurrentUser UserEntity user,
                                                                   @RequestBody SearchInSectionRequest request){
        /* no such section */
        long sectionId = request.getId();
        Optional<BbsSectionEntity> ret = bbsSectionRepository.findById(sectionId);
        if(!ret.isPresent())
            return new ResponseEntity<>(new SearchInSectionResponse("invalid id", null,
                                                null, null, null), HttpStatus.BAD_REQUEST);
        BbsSectionEntity section = ret.get();
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

        /* current user not in this section, can't search */
        if(!permission)
            return new ResponseEntity<>(new SearchInSectionResponse("permission denied", null, null, null,
                                            null), HttpStatus.FORBIDDEN);

        String[] keys = request.getKeyWords();

        List<Long> ids = new ArrayList<>();
        List<String> authorNames = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        List<Date> times = new ArrayList<>();

        Set<BbsTopicEntity> topics = section.getTopics();
        for(BbsTopicEntity t : topics){
           /* search topic */
            /* topic content match */
           if(BbsSearchController.contentMatch(keys, t.getContent())){
               ids.add(t.getId());
               authorNames.add(t.getAuthor().getName());
               contents.add(t.getContent());
               times.add(t.getTime());
           }

           /* list all it's reply */
           Set<BbsReplyEntity> replies = t.getReplies();
           for(BbsReplyEntity r : replies)
               /* search topic's replies */
               if(BbsSearchController.contentMatch(keys, r.getContent())){
                   ids.add(r.getId());
                   authorNames.add(r.getAuthor().getName());
                   contents.add(r.getContent());
                   times.add(r.getTime());
               }
        }

        return new ResponseEntity<>(new SearchInSectionResponse("ok", ids, authorNames, contents, times), HttpStatus.OK);
    }
}
