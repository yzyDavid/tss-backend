package tss.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author reeve
 */
@Entity
@Table(name = "classroom")
public class ClassroomEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "building_id")
    private BuildingEntity building;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArrangementEntity> arrangements = new ArrayList<>();

    public ClassroomEntity() {
    }

    public ClassroomEntity(String name, Integer capacity, BuildingEntity building) {
        this.name = name;
        this.capacity = capacity;
        this.building = building;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BuildingEntity getBuilding() {
        return building;
    }

    public void setBuilding(BuildingEntity building) {
        this.building = building;
    }

    public List<ArrangementEntity> getArrangements() {
        return arrangements;
    }


    // Utility methods.

    public void addArrangement(ArrangementEntity arrangementEntity) {
        arrangements.add(arrangementEntity);
        arrangementEntity.setClassroom(this);
    }
}
