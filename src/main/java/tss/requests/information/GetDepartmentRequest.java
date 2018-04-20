package tss.requests.information;

public class GetDepartmentRequest {
    private Short did;
    private String name;

    public Short getDid() {
        return did;
    }

    public void setDid(Short did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
