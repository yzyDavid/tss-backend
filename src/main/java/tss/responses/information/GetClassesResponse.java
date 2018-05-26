package tss.responses.information;

import org.jetbrains.annotations.Nls;
import tss.entities.ClassEntity;

import java.util.List;

public class GetClassesResponse {
/*
    @Nls
    private final String status;*/
    private final List<ClassEntity> classes;

    public GetClassesResponse(/*String status, */List<ClassEntity> classes) {
        //this.status = status;
        this.classes = classes;
    }

    public List<ClassEntity> getClasses() {
        return classes;
    }
}
