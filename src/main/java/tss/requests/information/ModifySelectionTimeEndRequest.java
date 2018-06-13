package tss.requests.information;

import java.sql.Timestamp;

public class ModifySelectionTimeEndRequest {
    private Long id;
    private Timestamp end;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }
}
