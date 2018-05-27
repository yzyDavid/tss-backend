package tss.responses.information;

import org.apache.tomcat.util.buf.StringUtils;
import org.jetbrains.annotations.Nls;
import tss.entities.ClassEntity;
import tss.entities.ClassroomEntity;
import tss.entities.SemesterEnum;
import tss.entities.TimeSlotEntity;

import tss.entities.ClassInfo;
import java.util.List;

public class GetClassesResponse {
    /* @Nls
    private final String status;*/
    private final List<ClassInfo> classes;

    public GetClassesResponse(List<ClassInfo> classes) {
        this.classes = classes;
    }

    public List<ClassInfo> getClasses() {
        return this.classes;
    }
}