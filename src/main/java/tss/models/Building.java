package tss.models;

import org.jetbrains.annotations.NotNull;
import tss.entities.BuildingEntity;
import tss.entities.CampusEntity;

/**
 * @author reeve
 */
public class Building {
    private Integer id;
    private String name;
    private Integer campusId;
    private String campusName;

    public Building() {
    }

    public Building(@NotNull BuildingEntity buildingEntity) {
        id = buildingEntity.getId();
        name = buildingEntity.getName();
        CampusEntity campusEntity = buildingEntity.getCampus();
        campusId = campusEntity.getId();
        campusName = campusEntity.getName();
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

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }
}
