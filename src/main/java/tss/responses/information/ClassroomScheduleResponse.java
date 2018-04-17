package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class ClassroomScheduleResponse {
    @Nls
    private final String status;
    private final String building;
    private final Integer room;
    private final List<String> courseName;
    private final List<String> day;
    private final List<Integer> start;
    private final List<Integer> end;
    private final List<String> teacherName;


    public ClassroomScheduleResponse(String status, String building, Integer room, List<String> courseName, List<String> day,
                                     List<Integer> start, List<Integer> end, List<String> teacherName) {
        this.status = status;
        this.building = building;
        this.room = room;
        this.courseName = courseName;
        this.day = day;
        this.start = start;
        this.end = end;
        this.teacherName = teacherName;
    }

    public String getStatus() {
        return status;
    }

    public String getBuilding() {
        return building;
    }

    public Integer getRoom() {
        return room;
    }

    public List<String> getCourseName() {
        return courseName;
    }

    public List<String> getDay() {
        return day;
    }

    public List<Integer> getStart() {
        return start;
    }

    public List<Integer> getEnd() {
        return end;
    }

    public List<String> getTeacherName() {
        return teacherName;
    }

}
