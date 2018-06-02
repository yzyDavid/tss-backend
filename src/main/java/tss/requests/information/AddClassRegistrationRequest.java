package tss.requests.information;

public class AddClassRegistrationRequest {
    private Long classId;

    public AddClassRegistrationRequest(Long classId) {
        this.classId = classId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
