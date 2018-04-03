package tss.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetTchCoursesResponse {
    @Nls
    private final String status;
    private final List<String> cid;
    private final List<String> name;
    private final List<Float> credit;
    private final List<Character> semester;
    private final List<Integer> capacity;


    GetTchCoursesResponse(String status, List<String> cid, List<String> name, List<Float> credit,
                          List<Character> semester, List<Integer> capacity) {
        this.status = status;
        this.cid = cid;
        this.name = name;
        this.credit = credit;
        this.semester = semester;
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getCid() {
        return cid;
    }

    public List<String> getName() {
        return name;
    }

    public List<Float> getCredit() {
        return credit;
    }

    public List<Character> getSemester() {
        return semester;
    }

    public List<Integer> getCapacity() {
        return capacity;
    }
}
