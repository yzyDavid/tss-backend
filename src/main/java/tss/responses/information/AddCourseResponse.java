package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddCourseResponse {
    private @Nls
    final String status;
    private final String cid;
    private final String name;

    public AddCourseResponse(String status, String cid, String name) {
        this.status = status;
        this.cid = cid;
        this.name = name;
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
}
