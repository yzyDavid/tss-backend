package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class BasicResponse {
    @Nls
    private final String status;

    public BasicResponse(String s) {
        status = s;
    }

    public String getStatus() {
        return status;
    }
}
