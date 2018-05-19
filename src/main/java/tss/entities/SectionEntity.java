package tss.entities;

import javax.persistence.*;

/**
 * @author Mingqi Yi
 * <p>
 * TODO: equals and hashCode method
 */
@Entity
@Table(name = "section")
public class SectionEntity {
    private Long id;
    private TimeSlotEntity timeSlot;
    private ClassroomEntity classroom;
    private ClassEntity _class;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "classroom_id")
    public ClassroomEntity getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomEntity classroom) {
        this.classroom = classroom;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "time_slot_id")
    public TimeSlotEntity getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlotEntity timeSlot) {
        this.timeSlot = timeSlot;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "class_id", nullable = false)
    public ClassEntity get_class() {
        return _class;
    }

    public void set_class(ClassEntity _class) {
        this._class = _class;
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        } else {
            return id.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass()) || id == null) {
            return false;
        } else {
            return (id.equals(((SectionEntity) obj).id));
        }
    }
}
