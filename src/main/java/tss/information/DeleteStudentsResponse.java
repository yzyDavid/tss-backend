package tss.information;

import org.jetbrains.annotations.Nls;

import java.util.Set;

public class DeleteStudentsResponse {
    @Nls private final String status;

    public DeleteStudentsResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
