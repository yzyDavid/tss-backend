package tss.information.untapped;

import tss.information.SectionEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classroom")
public class ClassroomEntity {
    short id;
    private String building;
    private short room;
    private short capactity;
    private Set<SectionEntity> sections = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Column(length = 64)
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public short getRoom() {
        return room;
    }

    public void setRoom(short room) {
        this.room = room;
    }

    public short getCapactity() {
        return capactity;
    }

    public void setCapactity(short capactity) {
        this.capactity = capactity;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classroom")
    public Set<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionEntity> sections) {
        this.sections = sections;
    }
}
