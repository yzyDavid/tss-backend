package tss.requests.information;

public class ModifyInstructorRequest {
    private String uid;
    private String cid;
    private Integer capacity;
    private Character semester;
    private Short timeSlotId;
    private Short classroomId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Character getSemester() {
        return semester;
    }

    public void setSemester(Character semester) {
        this.semester = semester;
    }

    public Short getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Short timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public Short getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Short classroomId) {
        this.classroomId = classroomId;
    }
}
