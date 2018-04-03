package tss.information;

public class GetStuCoursesRequest {
    private String uid;
    private int year;
    private char semester;
    private boolean needGrades;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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

    public boolean getNeedGrades() {
        return needGrades;
    }

    public void setNeedGrades(boolean needGrades) {
        this.needGrades = needGrades;
    }
}
