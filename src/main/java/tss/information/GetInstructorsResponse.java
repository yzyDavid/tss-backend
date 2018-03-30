package tss.information;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GetInstructorsResponse {
    @Nls private final String status;
    @Nullable
    private final List<String> tids;
    @Nullable
    private final List<String> names;
    @Nullable
    private final List<Integer> dates;
    @Nullable
    private final List<Integer> begintimes;
    @Nullable
    private final List<Integer> durations;
    @Nullable
    private final List<String> classrooms;

    GetInstructorsResponse(String status, List<String> tids, List<String> names, List<Integer> dates,
                           List<Integer> begintimes, List<Integer> durations, List<String> classrooms) {
        this.status = status;
        this.tids = tids;
        this.names = names;
        this.dates = dates;
        this.begintimes = begintimes;
        this.durations = durations;
        this.classrooms = classrooms;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getTids() {
        return tids;
    }

    public List<String> getNames() {
        return names;
    }

    public List<Integer> getDates() {
        return dates;
    }

    public List<Integer> getBegintimes() {
        return begintimes;
    }

    public List<Integer> getDurations() {
        return durations;
    }

    public List<String> getClassrooms() {
        return classrooms;
    }
}
