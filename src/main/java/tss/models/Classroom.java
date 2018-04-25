package tss.models;

import tss.entities.ClassroomEntity;

/**
 * @author reeve
 */
public class Classroom {
    private Integer id;
    private String name;
    private Integer capacity;

    public Classroom() {
    }

    public Classroom(Integer id, String name, Integer capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public Classroom(ClassroomEntity classroomEntity) {
        this(classroomEntity.getId(), classroomEntity.getName(), classroomEntity.getCapacity());
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
