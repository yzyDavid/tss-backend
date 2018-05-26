package tss.models;

import tss.entities.ClassEntity;
import tss.entities.ClassroomEntity;
import tss.entities.TimeSlotEntity;

/**
 * @author reeve
 */
public class TimeSlot {
    private TimeSlotTypeEnum type;
    private Long classId;
    private String courseName;
    private Integer classroomId;
    private String buildingName;
    private String campusName;

    public TimeSlot() {
    }

    public TimeSlot(TimeSlotEntity timeSlotEntity) {
        type = timeSlotEntity.getType();
        ClassEntity classEntity = timeSlotEntity.getClazz();
        classId = classEntity.getId();
        courseName = classEntity.getCourse().getName();
        ClassroomEntity classroomEntity = timeSlotEntity.getClassroom();
        classroomId = classroomEntity.getId();
        buildingName = classroomEntity.getBuilding().getName();
        campusName = classroomEntity.getBuilding().getCampus().getName();
    }

    public TimeSlotTypeEnum getType() {
        return type;
    }

    public void setType(TimeSlotTypeEnum type) {
        this.type = type;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }
}
