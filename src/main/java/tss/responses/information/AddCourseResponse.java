package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddCourseResponse {
    private @Nls
    final String status;
    private final String cid;
    private final String name;
    private final Float credit;
    private final Integer numLessonsEachWeek;
    private final String department;


    public AddCourseResponse(String status, String cid, String name, Float credit,
                             Integer numLessonsEachWeek, String department) {
        this.status = status;
        this.cid = cid;
        this.name = name;
        this.credit = credit;
        this.numLessonsEachWeek = numLessonsEachWeek;
        this.department = department;
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
}
