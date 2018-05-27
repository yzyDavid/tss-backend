package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class AddCourseinProgramResponse {
    private @Nls
    final String status;

    public AddCourseinProgramResponse(String status) {

        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
