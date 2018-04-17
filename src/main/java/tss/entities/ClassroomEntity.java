package tss.entities;

import javax.persistence.*;

@Entity
@Table(name = "classroom")
public class ClassroomEntity {
    private Integer id;
    private String building;
    private Integer room;
    private Integer capactity;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(length = 31, nullable = false)
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Column(nullable = false)
    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    @Column(nullable = false)
    public Integer getCapactity() {
        return capactity;
    }

    public void setCapactity(Integer capactity) {
        this.capactity = capactity;
    }
}
