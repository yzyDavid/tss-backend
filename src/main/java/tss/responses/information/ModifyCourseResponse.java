package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class ModifyCourseResponse {
    @Nls
    final String status;
    final String cid;
    final String name;
    private final Float credit;
    private final Integer weeklyNum;
    private final String department;
    private final String intro;

    public ModifyCourseResponse(String status, String cid, String name, Float credit,
                                Integer weeklyNum, String department, String intro) {
        this.status = status;
        this.cid = cid;
        this.name = name;
        this.credit = credit;
        this.weeklyNum = weeklyNum;
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

    public Integer getWeeklyNum() {
        return weeklyNum;
    }


    public Float getCredit() {
        return credit;
    }

    public String getIntro() {
        return intro;
    }
}
