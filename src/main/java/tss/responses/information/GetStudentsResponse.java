package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetStudentsResponse {
    @Nls
    private final String status;
    private final List<String> uids;
    private final List<String> names;
    private final List<Integer> scores;

    public GetStudentsResponse(String status, List<String> uids, List<String> names, List<Integer> scores) {
        this.status = status;
        this.uids = uids;
        this.names = names;
        this.scores = scores;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getUids() {
        return uids;
    }

    public List<String> getNames() {
        return names;
    }

    public List<Integer> getScores() {
        return scores;
    }
}
