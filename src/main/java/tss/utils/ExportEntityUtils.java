package tss.utils;

public class ExportEntityUtils {
    private String uid;
    private String name;
    private String gender;
    private String departmentname;
    private String majorclassname;
    private String telephone;
    private String email;

    public ExportEntityUtils(String uid, String name, String gender, String departmentname,
                             String majorclassname, String telephone, String email) {
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.departmentname = departmentname;
        this.majorclassname = majorclassname;
        this.telephone = telephone;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getMajorclassname() {
        return majorclassname;
    }

    public String getTelephone() {
        return telephone;
    }
}
