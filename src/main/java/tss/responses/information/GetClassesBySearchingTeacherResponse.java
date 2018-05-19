package tss.responses.information;

import org.jetbrains.annotations.Nls;
import tss.entities.ClassEntity;

import java.util.List;
import java.util.Set;

public class GetClassesBySearchingTeacherResponse {
    @Nls
    private final String status;
    private final Set<ClassEntity> classes;

    public GetClassesBySearchingTeacherResponse(String status, Set<ClassEntity> classes) {
        this.status = status;
        this.classes = classes;
    }

    public String getStatus() {
        return status;
    }

    public Set<ClassEntity> getClasses() {
        return classes;
    }
}
