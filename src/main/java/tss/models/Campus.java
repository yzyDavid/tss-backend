package tss.models;

import org.jetbrains.annotations.NotNull;
import tss.entities.CampusEntity;

/**
 * @author reeve
 */
public class Campus {
    private Integer id;
    private String name;

    public Campus() {
    }

    public Campus(@NotNull CampusEntity campusEntity) {
        id = campusEntity.getId();
        name = campusEntity.getName();
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
