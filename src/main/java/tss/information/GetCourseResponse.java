package tss.information;

import org.jetbrains.annotations.Nls;

public class GetCourseResponse {
    @Nls private final String status;
    private final String cid;
    private final String name;
    private final Float credit;
    private final Character semester;
    private final String intro;

    GetCourseResponse(String status, String cid, String name, Float credit,
                      Character semester, String intro) {
        this.status = status;
        this.cid = cid;
        this.name = name;
        this.credit = credit;
        this.semester = semester;
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

    public String getIntro() {
        return intro;
    }
}
