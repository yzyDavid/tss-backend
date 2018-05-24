package tss.models;

/**
 * @author reeve
 */
public class ClassroomTimeSlotTypePair {
    private Classroom classroom;
    private TimeSlotTypeEnum timeSlotType;

    public ClassroomTimeSlotTypePair(Classroom classroom, TimeSlotTypeEnum timeSlotType) {
        this.classroom = classroom;
        this.timeSlotType = timeSlotType;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public TimeSlotTypeEnum getTimeSlotType() {
        return timeSlotType;
    }

    public void setTimeSlotType(TimeSlotTypeEnum timeSlotType) {
        this.timeSlotType = timeSlotType;
    }
}
