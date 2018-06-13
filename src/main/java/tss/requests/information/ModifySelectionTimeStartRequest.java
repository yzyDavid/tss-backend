package tss.requests.information;

import java.sql.Timestamp;

public class ModifySelectionTimeStartRequest {
    private Long id;
    private Timestamp start;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }
}
