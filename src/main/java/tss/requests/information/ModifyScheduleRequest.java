package tss.requests.information;

/**
 * Created by apple on 2018/4/13.
 */
public class ModifyScheduleRequest {
    private long Sid;
    private String newBuilding;
    private Integer newRoom;
    private String newDay;
    private Integer newStart;
    private Integer newEnd;

    public long getSid(){return Sid;}
    public void setSid(long sid){this.Sid = sid;}

    public String getNewBuilding(){return newBuilding;}
    public void setNewBuilding(String newBuilding){this.newBuilding = newBuilding;}

    public Integer getNewRoom(){return  newRoom;}
    public void setNewRoom(Integer newRoom){this.newRoom = newRoom;}

    public String getNewDay(){return newDay;}
    public void setNewDay(String newDay){this.newDay = newDay;}

    public Integer getNewStart(){return newStart;}
    public void setNewStart(Integer newStart){this.newStart = newStart;}

    public Integer getNewEnd(){return newEnd;}
    public void setNewEnd(Integer newEnd){this.newEnd = newEnd;}

}
