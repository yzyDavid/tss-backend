package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "major", indexes = {
        @Index(name = "major_name_index", columnList = "major_name", unique = true)
})
public class MajorEntity {
    private Short id;
    private String name;
    private Integer numYears;
    private Integer credit;
    private Integer creditSelective;
    private Integer creditPublic;
    private DepartmentEntity department;
    private Set<CourseEntity> setOfCompulsory = new HashSet<>();
    private Set<CourseEntity> setOfSelective = new HashSet<>();

    @Id
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Column(name = "major_name", unique = true, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name_years")
    public Integer getNumYears() {
        return numYears;
    }

    public void setNumYears(Integer numYears) {
        this.numYears = numYears;
    }

    @Column(name = "total_credit")
    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    @Column(name = "selective_credit")
    public Integer getCreditSelective() {
        return creditSelective;
    }

    public void setCreditSelective(Integer creditSelective) {
        this.creditSelective = creditSelective;
    }

    @Column(name = "public_credit")
    public Integer getCreditPublic() {
        return creditPublic;
    }

    public void setCreditPublic(Integer creditPublic) {
        this.creditPublic = creditPublic;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "department_id")
    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "major")
    public Set<CourseEntity> getCompulsoryCourses() {
        return setOfCompulsory;
    }

    public void setCompulsoryCourses(Set<CourseEntity> courses) {
        this.setOfCompulsory = courses;
    }

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "major")
    public Set<CourseEntity> getSelectiveCourses() {
        return setOfSelective;
    }

    public void setSelectiveCourses(Set<CourseEntity> courses) {
        this.setOfSelective = courses;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(this.getClass()) && id != null) {
            return id.equals(((MajorClassEntity) obj).id);
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        if (name != null) {
            return name.hashCode();
        } else {
            return super.hashCode();
        }
    }
}
