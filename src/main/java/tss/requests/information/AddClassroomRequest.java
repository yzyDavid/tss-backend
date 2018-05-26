package tss.requests.information;

public class AddClassroomRequest {
    private String building;
    private Integer room;
    private Integer capacity;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Integer getRoom() {
        return room;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
