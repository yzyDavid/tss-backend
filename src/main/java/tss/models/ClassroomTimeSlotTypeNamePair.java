package tss.models;

/**
 * @author reeve
 */
public class ClassroomTimeSlotTypeNamePair {
    private Classroom classroom;
    private String timeSlotTypeName;

    public ClassroomTimeSlotTypeNamePair() {
    }

    public ClassroomTimeSlotTypeNamePair(Classroom classroom, String timeSlotTypeName) {
        this.classroom = classroom;
        this.timeSlotTypeName = timeSlotTypeName;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public String getTimeSlotTypeName() {
        return timeSlotTypeName;
    }

    public void setTimeSlotTypeName(String timeSlotTypeName) {
        this.timeSlotTypeName = timeSlotTypeName;
    }
}
