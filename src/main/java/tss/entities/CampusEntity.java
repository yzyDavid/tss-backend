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
    private Integer id;
    private String name;
    private List<BuildingEntity> buildings = new ArrayList<>();

    public CampusEntity() {
    }

    public CampusEntity(Integer id, String name, List<BuildingEntity> buildings) {
        this.id = id;
        this.name = name;
        this.buildings = buildings;
    }

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(length = 32, unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<BuildingEntity> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<BuildingEntity> buildings) {
        this.buildings = buildings;
    }
}
