package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class ModifyUserResponse {
    private final @Nls
    String status;
    private final String uid;
    private final String name;
    private final String gender;
    private final String majorClass;
    private final String type;
    private final String department;
    private final String email;
    private final String telephone;
    private final String intro;


    public ModifyUserResponse(String status, String uid, String name,
                              String gender, String majorClass, String type,
                              String department, String email, String telephone,
                              String intro) {
        this.status = status;
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.majorClass = majorClass;
        this.type = type;
        this.department = department;
        this.email = email;
        this.telephone = telephone;
        this.intro = intro;
    }

    public String getStatus() {
        return status;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getMajorClass() {
        return majorClass;
    }

    public String getGender() {
        return gender;
    }

    public String getType() {
        return type;
    }

    public String getDepartment() {
        return department;
    }

    public String getIntro() {
        return intro;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }
}
