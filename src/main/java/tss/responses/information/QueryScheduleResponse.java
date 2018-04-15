package tss.responses.information;

import org.jetbrains.annotations.Nls;
import java.util.List;


/**
 * Created by apple on 2018/4/13.
 */
public class QueryScheduleResponse {

    @Nls
    private final String status;
    private final String teacherName;
    private final List<String> courseName;
    private final List<String> day;
    private final List<Integer> start;
    private final List<Integer> end;
    private final List<String> building;
    private final List<Integer> room;


    public QueryScheduleResponse(String status, String teacherName,List<String> courseName, List<String> day,
                                 List<Integer> start, List<Integer> end, List<String> building, List<Integer> room){
        this.status = status;
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.day = day;
        this.start = start;
        this.end = end;
        this.building = building;
        this.room = room;
    }

    public String getStatus() {
        return status;
    }
    public String getTeacherName(){ return teacherName; }
    public List<String> getCourseName(){ return courseName; }
    public List<String> getDay(){ return  day; }
    public List<Integer> getStart(){ return start; }
    public List<Integer> getEnd(){ return  end; }
    public List<String> getBuilding(){ return building; }
    public List<Integer> getRoom(){ return room; }

}
