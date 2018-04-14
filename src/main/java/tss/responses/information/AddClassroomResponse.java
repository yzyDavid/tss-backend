package tss.responses.information;

import org.jetbrains.annotations.Nls;

/**
 * Created by apple on 2018/4/13.
 */
public class AddClassroomResponse {

    @Nls
    private final String status;
    private final Integer room;
    private final String cid;

    public AddClassroomResponse(String status, String cid, Integer room) {
        this.status = status;
        this.cid = cid;
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public Integer getRoom() { return room; }

    public String getCid()  { return  cid; }
}
