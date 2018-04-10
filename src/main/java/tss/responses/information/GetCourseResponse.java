package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class GetCourseResponse {
    @Nls
    private final String status;
    private final String cid;
    private final String name;
    private final Float credit;
    private final String semester;
    private final String intro;

    public GetCourseResponse(String status, String cid, String name, Float credit,
                             String semester, String intro) {
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

    public String getSemester() {
        return semester;
    }

    public String getIntro() {
        return intro;
    }
}
