package tss.requests.information;

public class GetAllClassRequest {
    private String uid;
    private String semester;

//    public GetAllClassRequest(String uid, String semester) {
//        this.uid = uid;
//        this.semester = semester;
//        this.semester = semester;
//    }

    public String getUid() {
        return uid;
    }

    public String getSemester() {
        return semester;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
