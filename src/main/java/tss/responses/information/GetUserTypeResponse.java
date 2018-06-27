package tss.responses.information;

public class GetUserTypeResponse {
    private String status;
    private String typename;

    public GetUserTypeResponse(String status){
        this.status = status;
        this.typename = null;
    }

    public GetUserTypeResponse(String status, String typename){
        this.status = status;
        this.typename = typename;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type){this.typename = type;}
    public String getType(){return typename;}

}
