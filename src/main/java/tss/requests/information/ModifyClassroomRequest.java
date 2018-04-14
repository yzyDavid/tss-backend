package tss.requests.information;

/**
 * Created by apple on 2018/4/13.
 */
public class ModifyClassroomRequest {
    private short cid;
    private String building;
    private Integer room;
    private Integer capacity;

    public short getCid() {
        return cid;
    }

    public void setCid(short cid) {
        this.cid = cid;
    }

    public String getBuilding(){ return building; }

    public void setBuilding(String building) { this.building = building; }

    public void setRoom(Integer room) {this.room = room;}

    public Integer getRoom() {return room;}

    public void setCapacity(Integer capacity) {this.capacity = capacity;}

    public Integer getCapacity() {return capacity;}
}
