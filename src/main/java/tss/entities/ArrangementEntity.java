package tss.entities;

import tss.models.TimeSlotEnum;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 * @author reeve
 */
@Entity
public class ArrangementEntity {

    @EmbeddedId
    private ArrangementId id;

    @ManyToOne(optional = false)
    @MapsId("classId")
    private ClassEntity clazz;

    @ManyToOne(optional = false)
    @MapsId("classroomId")
    private ClassroomEntity classroom;

    public ArrangementEntity() {
    }

    public ArrangementEntity(ClassEntity clazz, ClassroomEntity classroom, TimeSlotEnum timeSlot) {
        this.id = new ArrangementId(clazz.getId(), classroom.getId(), timeSlot.name());
        this.clazz = clazz;
        this.classroom = classroom;
    }


    // Getter and setter.

    public ClassEntity getClazz() {
        return clazz;
    }

    public void setClazz(ClassEntity clazz) {
        id.setClassId(clazz.getId());
        this.clazz = clazz;
    }

    public ClassroomEntity getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomEntity classroom) {
        id.setClassroomId(classroom.getId());
        this.classroom = classroom;
    }


    // Utility methods.

    public void setTimeSlot(TimeSlotEnum timeSlot) {
        id.setTimeSlotName(timeSlot.name());
    }

    public TimeSlotEnum getTimeSlot() {
        return TimeSlotEnum.valueOf(id.getTimeSlotName());
    }
}
