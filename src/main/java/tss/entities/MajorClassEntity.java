package tss.entities;

import org.hibernate.property.access.spi.SetterMethodImpl;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "major_class", indexes = {
@Index(name = "class_name_index", columnList = "class_name", unique = true)
})
public class MajorClassEntity {
    Short id;
    String name;
    Integer year;
    MajorEntity major;
    Set<UserEntity> students = new HashSet<>();

    @Id
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Column(name = "class_name", length = 20, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "major_id")
    public MajorEntity getMajor() {
        return major;
    }

    public void setMajor(MajorEntity major) {
        this.major = major;
    }

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "majorClass")
    public Set<UserEntity> getStudents() {
        return students;
    }

    public void setStudents(Set<UserEntity> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(this.getClass()) && id != null) {
            return id.equals(((MajorClassEntity)obj).id);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if(name != null) {
            return name.hashCode();
        } else {
            return super.hashCode();
        }
    }
}
