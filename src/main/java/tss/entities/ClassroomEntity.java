package tss.entities;

import javax.persistence.*;

/**
 * @author reeve
 */
@Entity
@Table(name = "classroom")
public class ClassroomEntity {
    private Integer id;
    private String name;
    private Integer capacity;
    private BuildingEntity building;

    public ClassroomEntity() {
    }

    public ClassroomEntity(Integer id, String name, Integer capacity, BuildingEntity building) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.building = building;
    }

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(length = 32, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "building_id")
    public BuildingEntity getBuilding() {
        return building;
    }

    public void setBuilding(BuildingEntity building) {
        this.building = building;
    }
}
