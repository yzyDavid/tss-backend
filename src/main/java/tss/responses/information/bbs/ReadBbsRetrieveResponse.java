package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

import java.util.Date;

public class ReadBbsRetrieveResponse {
    @Nls
    private final String status;

    public ReadBbsRetrieveResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
