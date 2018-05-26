package tss.requests.information;

public class GetClassesBySearchingTeacherRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer year;

    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getYear() {
        return year;
    }
}
