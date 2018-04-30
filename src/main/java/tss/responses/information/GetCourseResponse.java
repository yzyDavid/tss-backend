package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class GetCourseResponse {
    @Nls
    private final String status;
    private final String cid;
    private final String name;
    private final Float credit;
    private final Integer numLessonsEachWeek;
    private final String intro;

    public GetCourseResponse(String status, String cid, String name, Float credit,
                             Integer numLessonsEachWeek, String intro) {
        this.status = status;
        this.cid = cid;
        this.name = name;
        this.credit = credit;
        this.numLessonsEachWeek = numLessonsEachWeek;
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

    public Integer getNumLessonsEachWeek() {
        return numLessonsEachWeek;
    }

    public String getIntro() {
        return intro;
    }
}
