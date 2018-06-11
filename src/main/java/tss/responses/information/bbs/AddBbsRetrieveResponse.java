package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.Date;

public class AddBbsRetrieveResponse {
    @Nls
    private final String status;

    public AddBbsRetrieveResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
