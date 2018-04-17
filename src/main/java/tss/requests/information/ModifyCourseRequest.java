package tss.requests.information;

import org.jetbrains.annotations.NotNull;

public class ModifyCourseRequest {
    @NotNull
    private String cid;
    private String name;
    private Float credit;
    private Integer weeklyNum;
    private String semester;
    private String intro;
    private String dept;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public Integer getWeeklyNum() {
        return weeklyNum;
    }

    public void setWeeklyNum(Integer weeklyNum) {
        this.weeklyNum = weeklyNum;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
