package tss.information;

import org.jetbrains.annotations.Nls;

public class GetCourseResponse {
    @Nls private final String status;
    private final String cid;
    private final String name;
    private final Float credit;
    private final Character semester;
    private final Integer capacity;
    private final String intro;

    GetCourseResponse(String status, String cid, String name, Float credit,
                      Character semester, Integer capacity, String intro) {
        this.status = status;
        this.cid = cid;
        this.name = name;
        this.credit = credit;
        this.semester = semester;
        this.capacity = capacity;
        this.intro = intro;
    }

    public String getStatus() {
        return status;
    }

    public String getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public Float getCredit() {
        return credit;
    }

    public Character getSemester() {
        return semester;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getIntro() {
        return intro;
    }
}
