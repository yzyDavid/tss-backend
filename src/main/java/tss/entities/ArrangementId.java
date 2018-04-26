package tss.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author reeve
 */
@Embeddable
public class ArrangementId implements Serializable {

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "classroom_id")
    private Integer classroomId;

    @Column(name = "time_slot_name")
    private String timeSlotName;

    public ArrangementId() {
    }

    public ArrangementId(Long classId, Integer classroomId, String timeSlotName) {
        this.classId = classId;
        this.classroomId = classroomId;
        this.timeSlotName = timeSlotName;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    public String getTimeSlotName() {
        return timeSlotName;
    }

    public void setTimeSlotName(String timeSlotName) {
        this.timeSlotName = timeSlotName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArrangementId that = (ArrangementId) o;
        return Objects.equals(classId, that.classId) && Objects.equals(classroomId, that.classroomId)
                && Objects.equals(timeSlotName, that.timeSlotName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classId, classroomId, timeSlotName);
    }
}
