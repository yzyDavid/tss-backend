package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class ModifyCourseResponse {
    @Nls
    final String status;
    final String cid;
    final String name;
    private final Float credit;
    private final Integer numLessonsEachWeek;
    private final String department;
    private final String intro;

    public ModifyCourseResponse(String status, String cid, String name, Float credit,
                                Integer numLessonsEachWeek, String department, String intro) {
        this.status = status;
        this.cid = cid;
        this.name = name;
        this.credit = credit;
        this.numLessonsEachWeek = numLessonsEachWeek;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public Integer getNumLessonsEachWeek() {
        return numLessonsEachWeek;
    }

    public Float getCredit() {
        return credit;
    }

    public String getIntro() {
        return intro;
    }
}
