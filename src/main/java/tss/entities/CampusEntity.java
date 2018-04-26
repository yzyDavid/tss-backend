package tss.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author reeve
 */
@Entity
@Table(name = "campus")
public class CampusEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 32, unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BuildingEntity> buildings = new ArrayList<>();

    public CampusEntity() {
    }

    public CampusEntity(String name) {
        this.name = name;
    }

    // Getter and setter.

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

    public List<BuildingEntity> getBuildings() {
        return buildings;
    }


    // Utility methods.

    public void addBuilding(BuildingEntity buildingEntity) {
        buildings.add(buildingEntity);
        buildingEntity.setCampus(this);
    }
}
