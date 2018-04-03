package tss.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetStuCoursesResponse {
    @Nls
    private final String status;
    private final List<String> cids;
    private final List<String> names;
    private final List<Float> credits;
    private final List<Integer> grades;

    public GetStuCoursesResponse(String status, List<String> cids, List<String> names,
                                 List<Float> credits, List<Integer> grades) {
        this.status = status;
        this.cids = cids;
        this.names = names;
        this.credits = credits;
        this.grades = grades;
    }

    public String getStatus() {
        return status;
    }

    public List<Float> getCredits() {
        return credits;
    }

    public List<String> getNames() {
        return names;
    }

    public List<String> getCids() {
        return cids;
    }

    public List<Integer> getGrades() {
        return grades;
    }
}
