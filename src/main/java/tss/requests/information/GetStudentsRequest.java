package tss.requests.information;

public class GetStudentsRequest {
    private int year;
    private char semester;
    private String cid;
    private String uid;
    private boolean needGrades;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public char getSemester() {
        return semester;
    }

    public void setSemester(char semester) {
        this.semester = semester;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean getNeedGrades() {
        return this.needGrades;
    }

    public void setNeedGrades(boolean needGrades) {
        this.needGrades = needGrades;
    }
}
