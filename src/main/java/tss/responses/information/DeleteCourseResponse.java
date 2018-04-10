package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class DeleteCourseResponse {
    @Nls
    final String status;

    public DeleteCourseResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
