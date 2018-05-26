package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class ModifyMajorClassResponse {
    @Nls
    final private String status;
    final private String name;
    final private String newName;
    final private String major;
    final private Integer year;

    public ModifyMajorClassResponse(String status, String name, String newName, String major, Integer year) {
        this.status = status;
        this.name = name;
        this.newName = newName;
        this.major = major;
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getNewName() {
        return newName;
    }

    public String getMajor() {
        return major;
    }

    public Integer getYear() {
        return year;
    }
}
