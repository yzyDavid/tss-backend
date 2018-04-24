package tss.models;

import tss.entities.BuildingEntity;

/**
 * @author reeve
 */
public class Building {
    private Integer id;
    private String name;

    public Building() {
    }

    public Building(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Building(BuildingEntity buildingEntity) {
        this(buildingEntity.getId(), buildingEntity.getName());
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
}
