package tss.responses.information;

import org.jetbrains.annotations.Nls;

import java.util.List;

public class GetMajorClassResponse {
    @Nls
    final private String status;
    final private String name;
    final private String major;
    final private Integer year;
    final private List<String> uids;
    final private List<String> unames;

    public GetMajorClassResponse(String status, String name, String major, Integer year,
                                 List<String> uids, List<String> unames) {
        this.status = status;
        this.name = name;
        this.major = major;
        this.year = year;
        this.uids = uids;
        this.unames = unames;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public List<String> getUids() {
        return uids;
    }

    public List<String> getUnames() {
        return unames;
    }

    public Integer getYear() {
        return year;
    }

    public String getMajor() {
        return major;
    }
}
