package tss.responses.information.bbs;

import org.jetbrains.annotations.Nls;

public class AddBbsSectionResponce {
    @Nls
    private final String status;
    private final long id;
    private final String name;
    private final String tid;

    public AddBbsSectionResponce(String status, long id, String name, String tid){
        this.status = status;
        this.id = id;
        this.name = name;
        this.tid = tid;
    }

    public String getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTid() {
        return tid;
    }
}
