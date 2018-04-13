package tss.responses.information;

import org.jetbrains.annotations.Nls;

/**
 * Created by apple on 2018/4/13.
 */
public class AdjustScheduleResponse {

    @Nls
    private final String status;

    public AdjustScheduleResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
