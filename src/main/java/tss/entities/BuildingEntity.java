package tss.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author reeve
 */
@Entity
@Table(name = "building")
public class BuildingEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 32, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "campus_id")
    private CampusEntity campus;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassroomEntity> classrooms = new ArrayList<>();

    public BuildingEntity() {
    }

    public BuildingEntity(String name, CampusEntity campus) {
        this.name = name;
        this.campus = campus;
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

    public CampusEntity getCampus() {
        return campus;
    }

    public void setCampus(CampusEntity campus) {
        this.campus = campus;
    }

    public List<ClassroomEntity> getClassrooms() {
        return classrooms;
    }

    public void addClassroom(ClassroomEntity classroomEntity) {
        classrooms.add(classroomEntity);
        classroomEntity.setBuilding(this);
    }
}
