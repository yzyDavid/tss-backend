package tss.information;

import org.jetbrains.annotations.Nullable;

public class ModifyCourseRequest {
    private String cid;
    @Nullable
    private String name;
    @Nullable
    private Float credit;
    @Nullable
    private Character semester;
    @Nullable
    private String intro;

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

    public Character getSemester() {
        return semester;
    }

    public void setSemester(Character semester) {
        this.semester = semester;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
