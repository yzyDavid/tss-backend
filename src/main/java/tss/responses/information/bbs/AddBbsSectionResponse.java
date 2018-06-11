package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

public class AddBbsSectionResponse {
    @Nls
    private final String status;
    private final String id;
    private final String name;
    private final String tname;

    public AddBbsSectionResponse(String status, String id, String name, String tname) {
        this.status = status;
        this.id = id;
        this.name = name;
        this.tname = tname;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTname() {
        return tname;
    }
}
