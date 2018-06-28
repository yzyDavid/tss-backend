package tss.responses.information.bbs;

public class ShowUserInfoResponse {
    private final String userName;
    private final String photo;
    private final String email;
    private final String tel;
    private final String description;
    private final String postNum;

    public ShowUserInfoResponse(String userName, String photo, String email, String tel, String description, String postNum) {
        this.userName = userName;
        this.photo = photo;
        this.email = email;
        this.tel = tel;
        this.description = description;
        this.postNum = postNum;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getDescription() {
        return description;
    }

    public String getPostNum() {
        return postNum;
    }
}
