package tss.controllers.bbs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.entities.bbs.BbsTopicEntity;
import tss.repositories.UserRepository;
import tss.repositories.bbs.BbsReplyRepository;
import tss.repositories.bbs.BbsSectionRepository;
import tss.repositories.bbs.BbsTopicRepository;
import tss.responses.information.bbs.ListHotResponse;

import java.text.SimpleDateFormat;
import java.util.*;

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

    private static ArrayList<BbsTopicEntity> bubbleSort(ArrayList<BbsTopicEntity> array) {
        BbsTopicEntity temp;
        for (int i = 0; i < array.size() - 1; i++) {
            for (int j = 0; j < array.size() - 1 - i; j++) {
                if (array.get(j).getReplyNum() > array.get(j + 1).getReplyNum()) {
                    temp = array.get(j);
                    array.set(j, array.get(j + 1));
                    array.set(j + 1, temp);
                }
            }
        }
        return array;
    }

    private static ArrayList<BbsTopicEntity> bubbleSortTime(ArrayList<BbsTopicEntity> array) {
        BbsTopicEntity temp;
        SimpleDateFormat fd = new SimpleDateFormat("yyyyMMddHHmmss");
        for (int i = 0; i < array.size() - 1; i++) {
            for (int j = 0; j < array.size() - 1 - i; j++) {
                Calendar calendarL = Calendar.getInstance();
                calendarL.setTime(array.get(j).getTime());
                String L = fd.format(calendarL.getTime());

                Calendar calendarR = Calendar.getInstance();
                calendarR.setTime(array.get(j+1).getTime());
                String R = fd.format(calendarR.getTime());

                if (L.compareTo(R) > 0) {
                    temp = array.get(j);
                    array.set(j, array.get(j + 1));
                    array.set(j + 1, temp);
                }
            }
        }
        return array;
    }

    @GetMapping(path = "/topic")
    public ResponseEntity<ListHotResponse> showHot() {
        Iterator<BbsTopicEntity> itor = topicRepository.findAll().iterator();
        ArrayList<BbsTopicEntity> array = new ArrayList<>();
        while (itor.hasNext()) {
            BbsTopicEntity topic = itor.next();
            array.add(topic);
        }
        ArrayList<BbsTopicEntity> afterSort;
        afterSort = bubbleSort(array);
        int size = afterSort.size();
        List<String> boardNames = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<String> boardids = new ArrayList<>();
        List<String> topicids = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> replyNUMs = new ArrayList<>();
        List<String> lastReplyTimes = new ArrayList<>();

        int i;
        /* FIXME Magic Number, top 5 */
        for (i = 0; i < 5; i++) {
            BbsTopicEntity t = afterSort.get(size - 1 - i);
            boardNames.add(t.getBelongedSection().getName());
            titles.add(t.getName());
            authors.add(t.getAuthor().getName());
            boardids.add(String.valueOf(t.getBelongedSection().getId()));
            topicids.add(String.valueOf(t.getId()));
            times.add(t.getTime().toString());
            replyNUMs.add(String.valueOf(t.getReplyNum()));
            lastReplyTimes.add(String.valueOf(t.getLastReplyTime().toInstant()));
        }
        return new ResponseEntity<>(new ListHotResponse(boardNames, titles, authors, boardids, topicids, times, replyNUMs, lastReplyTimes), HttpStatus.OK);
    }

    @GetMapping(path = "/hnum")
    public ResponseEntity<ListHotResponse> listHourHot() {
        SimpleDateFormat fd = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();

        String now = fd.format(calendar.getTime());
        calendar.add(Calendar.HOUR, -1);
        String monthago = fd.format(calendar.getTime());

        Iterator<BbsTopicEntity> itor = topicRepository.findAll().iterator();
        ArrayList<BbsTopicEntity> array = new ArrayList<>();
        while (itor.hasNext()) {
            BbsTopicEntity topic = itor.next();
            Calendar c = Calendar.getInstance();
            c.setTime(topic.getTime());

            String ttime =fd.format(c.getTime());
            if(ttime.compareTo(monthago) > 0){
                array.add(topic);
            }
        }

        /* sort */
        ArrayList<BbsTopicEntity> afterSort = bubbleSort(array);
        int size = afterSort.size();
        List<String> boardNames = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<String> boardids = new ArrayList<>();
        List<String> topicids = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> replyNUMs = new ArrayList<>();
        List<String> lastReplyTimes = new ArrayList<>();

        int i;
        /* FIXME Magic Number, top 5 */
        for (i = 0; i < 5; i++) {
            BbsTopicEntity t = afterSort.get(size - 1 - i);
            boardNames.add(t.getBelongedSection().getName());
            titles.add(t.getName());
            authors.add(t.getAuthor().getName());
            boardids.add(String.valueOf(t.getBelongedSection().getId()));
            topicids.add(String.valueOf(t.getId()));
            times.add(t.getTime().toString());
            replyNUMs.add(String.valueOf(t.getReplyNum()));
            lastReplyTimes.add(String.valueOf(t.getLastReplyTime().toInstant()));
        }
        return new ResponseEntity<>(new ListHotResponse(boardNames, titles, authors, boardids, topicids, times, replyNUMs, lastReplyTimes), HttpStatus.OK);
    }

    @GetMapping(path = "/wnum")
    public ResponseEntity<ListHotResponse> listWeekHot() {
        SimpleDateFormat fd = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();

        String now = fd.format(calendar.getTime());
        calendar.add(Calendar.DATE, -7);
        String monthago = fd.format(calendar.getTime());

        Iterator<BbsTopicEntity> itor = topicRepository.findAll().iterator();
        ArrayList<BbsTopicEntity> array = new ArrayList<>();
        while (itor.hasNext()) {
            BbsTopicEntity topic = itor.next();
            Calendar c = Calendar.getInstance();
            c.setTime(topic.getTime());

            String ttime =fd.format(c.getTime());
            if(ttime.compareTo(monthago) > 0){
                array.add(topic);
            }
        }

        /* sort */
        ArrayList<BbsTopicEntity> afterSort = bubbleSort(array);
        int size = afterSort.size();
        List<String> boardNames = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<String> boardids = new ArrayList<>();
        List<String> topicids = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> replyNUMs = new ArrayList<>();
        List<String> lastReplyTimes = new ArrayList<>();

        int i;
        /* FIXME Magic Number, top 5 */
        for (i = 0; i < 5; i++) {
            BbsTopicEntity t = afterSort.get(size - 1 - i);
            boardNames.add(t.getBelongedSection().getName());
            titles.add(t.getName());
            authors.add(t.getAuthor().getName());
            boardids.add(String.valueOf(t.getBelongedSection().getId()));
            topicids.add(String.valueOf(t.getId()));
            times.add(t.getTime().toString());
            replyNUMs.add(String.valueOf(t.getReplyNum()));
            lastReplyTimes.add(String.valueOf(t.getLastReplyTime().toInstant()));
        }
        return new ResponseEntity<>(new ListHotResponse(boardNames, titles, authors, boardids, topicids, times, replyNUMs, lastReplyTimes), HttpStatus.OK);
    }

    @GetMapping(path = "/mnum")
    public ResponseEntity<ListHotResponse> listMonthHot() {
        SimpleDateFormat fd = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();

        String now = fd.format(calendar.getTime());
        calendar.add(Calendar.DATE, -30);
        String monthago = fd.format(calendar.getTime());

        Iterator<BbsTopicEntity> itor = topicRepository.findAll().iterator();
        ArrayList<BbsTopicEntity> array = new ArrayList<>();
        while (itor.hasNext()) {
            BbsTopicEntity topic = itor.next();
            Calendar c = Calendar.getInstance();
            c.setTime(topic.getTime());

            String ttime =fd.format(c.getTime());
            if(ttime.compareTo(monthago) > 0){
                array.add(topic);
            }
        }

        /* sort */
        ArrayList<BbsTopicEntity> afterSort = bubbleSort(array);
        int size = afterSort.size();
        List<String> boardNames = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<String> boardids = new ArrayList<>();
        List<String> topicids = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> replyNUMs = new ArrayList<>();
        List<String> lastReplyTimes = new ArrayList<>();

        int i;
        /* FIXME Magic Number, top 5 */
        for (i = 0; i < 5; i++) {
            BbsTopicEntity t = afterSort.get(size - 1 - i);
            boardNames.add(t.getBelongedSection().getName());
            titles.add(t.getName());
            authors.add(t.getAuthor().getName());
            boardids.add(String.valueOf(t.getBelongedSection().getId()));
            topicids.add(String.valueOf(t.getId()));
            times.add(t.getTime().toString());
            replyNUMs.add(String.valueOf(t.getReplyNum()));
            lastReplyTimes.add(String.valueOf(t.getLastReplyTime().toInstant()));
        }
        return new ResponseEntity<>(new ListHotResponse(boardNames, titles, authors, boardids, topicids, times, replyNUMs, lastReplyTimes), HttpStatus.OK);
    }

    @GetMapping(path = "/new")
    public ResponseEntity<ListHotResponse> listNew(){
        Iterator<BbsTopicEntity> itor = topicRepository.findAll().iterator();
        ArrayList<BbsTopicEntity> array = new ArrayList<>();
        while (itor.hasNext()) {
            BbsTopicEntity topic = itor.next();
            array.add(topic);
        }

        /* sort */
        ArrayList<BbsTopicEntity> afterSort = bubbleSortTime(array);
        int size = afterSort.size();
        List<String> boardNames = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<String> boardids = new ArrayList<>();
        List<String> topicids = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> replyNUMs = new ArrayList<>();
        List<String> lastReplyTimes = new ArrayList<>();

        int i;
        /* FIXME Magic Number, top 5 */
        for (i = 0; i < 5; i++) {
            BbsTopicEntity t = afterSort.get(size - 1 - i);
            boardNames.add(t.getBelongedSection().getName());
            titles.add(t.getName());
            authors.add(t.getAuthor().getName());
            boardids.add(String.valueOf(t.getBelongedSection().getId()));
            topicids.add(String.valueOf(t.getId()));
            times.add(t.getTime().toString());
            replyNUMs.add(String.valueOf(t.getReplyNum()));
            lastReplyTimes.add(String.valueOf(t.getLastReplyTime().toInstant()));
        }
        return new ResponseEntity<>(new ListHotResponse(boardNames, titles, authors, boardids, topicids, times, replyNUMs, lastReplyTimes), HttpStatus.OK);
    }
}
