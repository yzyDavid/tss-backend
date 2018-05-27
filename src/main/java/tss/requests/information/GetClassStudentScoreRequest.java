package tss.requests.information;

public class GetClassStudentScoreRequest {
    private String uid;
    private String semester;
    private Integer year;


    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getUid() {

        return uid;
    }

    public String getSemester() {
        return semester;
    }

    public Integer getYear() {
        return year;
    }
}
