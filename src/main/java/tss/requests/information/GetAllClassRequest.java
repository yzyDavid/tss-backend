package tss.requests.information;

public class GetAllClassRequest {
    private String uid;
    private Integer year;
    private String semester;

    public GetAllClassRequest(String uid, Integer year, String semester) {
        this.uid = uid;
        this.year = year;
        this.semester = semester;
    }

    public GetAllClassRequest() {
    }

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

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getYear() {

        return year;
    }
}
