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
    private Integer id;
    private String name;
    private CampusEntity campus;
    private List<ClassroomEntity> classrooms = new ArrayList<>();

    public BuildingEntity() {
    }

    public BuildingEntity(Integer id, String name, CampusEntity campus, List<ClassroomEntity> classrooms) {
        this.id = id;
        this.name = name;
        this.campus = campus;
        this.classrooms = classrooms;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "campus_id")
    public CampusEntity getCampus() {
        return campus;
    }

    public void setCampus(CampusEntity campus) {
        this.campus = campus;
    }

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ClassroomEntity> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<ClassroomEntity> classrooms) {
        this.classrooms = classrooms;
    }
}
