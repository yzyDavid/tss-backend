package tss.requests.information;

public class GetClassesBySearchingBothRequest {
    private String teacherName;
    private String courseName;

    public String getTeacherName() {
        return teacherName;
    }

    public String getCourseName() {
        return courseName;
    }


    public void setTeacherName(String name) {
        this.teacherName = name;
    }

    public void setCourseName(String name) {
        this.courseName = name;
    }

    private Integer year;

    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getYear() {
        return year;
    }
}
