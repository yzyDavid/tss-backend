package tss.information;

import org.jetbrains.annotations.Nls;

public class DeleteCourseResponse {
    @Nls
    final String status;
    final String cid;
    final String name;

    DeleteCourseResponse(String status, String cid, String name) {
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
