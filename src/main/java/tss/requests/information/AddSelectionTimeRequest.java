package tss.requests.information;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class AddSelectionTimeRequest {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp start;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp end;

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }
}
