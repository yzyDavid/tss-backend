package tss.responses.information;

import org.jetbrains.annotations.Nls;

public class ModifyMajorResponse {
    @Nls
    final private String status;
    final private String name;
    final private String newName;
    final private String department;

    public ModifyMajorResponse(String status, String name, String newName, String department) {
        this.status = status;
        this.name = name;
        this.newName = newName;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

}
