package tss.requests.information;

import tss.entities.TeachesEntity;
import tss.responses.information.QueryScheduleResponse;

/**
 * Created by apple on 2018/4/13.
 */
public class QueryScheduleRequest {
    String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
