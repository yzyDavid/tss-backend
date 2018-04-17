package tss.responses.information;

import org.jetbrains.annotations.Nls;

/**
 * @author reeve
 */
public class ClassroomResponse {
    @Nls
    private final String status;
    private final Integer classroomId;
    private final Integer room;
    private final Integer capacity;

    public ClassroomResponse(String status, Integer classroomId, Integer room, Integer capacity) {
        this.status = status;
        this.classroomId = classroomId;
        this.room = room;
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public Integer getRoom() {
        return room;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
