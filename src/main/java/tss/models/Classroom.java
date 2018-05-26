package tss.models;

import org.jetbrains.annotations.NotNull;
import tss.entities.BuildingEntity;
import tss.entities.ClassroomEntity;

/**
 * @author reeve
 */
public class Classroom {
    private Integer id;
    private String name;
    private Integer capacity;
    private Integer buildingId;
    private String buildingName;

    public Classroom() {
    }

    public Classroom(@NotNull ClassroomEntity classroomEntity) {
        id = classroomEntity.getId();
        name = classroomEntity.getName();
        capacity = classroomEntity.getCapacity();
        BuildingEntity buildingEntity = classroomEntity.getBuilding();
        buildingId = buildingEntity.getId();
        buildingName = buildingEntity.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
