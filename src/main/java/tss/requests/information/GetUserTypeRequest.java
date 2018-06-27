package tss.requests.information;

public class GetUserTypeRequest {
    private String uid;

    public GetUserTypeRequest(){

    }

    public GetUserTypeRequest(String uid){
        this.uid = uid;
    }

    public void setUid(String uid){this.uid = uid;}
    public String getUid(){return uid;};
}
