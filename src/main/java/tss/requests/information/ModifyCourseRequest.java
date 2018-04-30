package tss.requests.information;

import org.jetbrains.annotations.NotNull;

public class ModifyCourseRequest {
    @NotNull
    private String cid;
    private String name;
    private Float credit;
    private Integer numLessonsEachWeek;
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

    public Integer getNumLessonsEachWeek() {
        return numLessonsEachWeek;
    }

    public void setNumLessonsEachWeek(Integer numLessonsEachWeek) {
        this.numLessonsEachWeek = numLessonsEachWeek;
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
