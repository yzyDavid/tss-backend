package tss.information;

import org.jetbrains.annotations.Nls;

public class GetCourseResponse {
    @Nls private final String status;
    private final String cid;
    private final String name;
    private final int credit;
    private final int semester;
    private final int capacity;
    private final String intro;

    GetCourseResponse(String status, String cid, String name, int credit,
                      int semester, int capacity, String intro) {
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

    public int getCredit() {
        return credit;
    }

    public int getSemester() {
        return semester;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getIntro() {
        return intro;
    }
}
