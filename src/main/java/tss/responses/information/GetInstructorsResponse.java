package tss.responses.information;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GetInstructorsResponse {
    @Nls
    private final String status;
    @Nullable
    private final List<Long> ids;
    @Nullable
    private final List<String> tids;
    @Nullable
    private final List<String> names;
    @Nullable
    private final List<List<String>> times;
    @Nullable
    private final List<List<String>> classrooms;

    public GetInstructorsResponse(String status, List<Long> ids, List<String> tids, List<String> names,
                                  List<List<String>> times, List<List<String>> classrooms) {
        this.status = status;
        this.ids = ids;
        this.tids = tids;
        this.names = names;
        this.times = times;
        this.classrooms = classrooms;
    }

    public String getStatus() {
        return status;
    }

    public List<Long> getIds() {
        return ids;
    }

    public List<String> getTids() {
        return tids;
    }

    public List<String> getNames() {
        return names;
    }


    public List<List<String>> getTimes() {
        return times;
    }

    public List<List<String>> getClassrooms() {
        return classrooms;
    }
}
