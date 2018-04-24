package tss.models;

import tss.entities.CampusEntity;

/**
 * @author reeve
 */
public class Campus {
    private Integer id;
    private String name;

    public Campus() {
    }

    public Campus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Campus(CampusEntity campusEntity) {
        this(campusEntity.getId(), campusEntity.getName());
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
