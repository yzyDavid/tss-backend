package tss.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingqi Yi
 */
@Entity
@Table(
        name = "course",
        indexes = {
                @Index(name = "course_name_index", columnList = "name")
        }
)
public class CourseEntity {
    private String id;
    private String name;
    private Float credit;
    private Integer numLessonsEachWeek;
    private String intro;
    private DepartmentEntity department;
    private List<ClassEntity> classes = new ArrayList<>();

    @Id
    @Column(name = "id", length = 10)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "credit")
    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    @Column(name = "num_lessons_each_week")
    public Integer getNumLessonsEachWeek() {
        return numLessonsEachWeek;
    }

    public void setNumLessonsEachWeek(Integer numLessonsEachWeek) {
        this.numLessonsEachWeek = numLessonsEachWeek;
    }

    @Column(name = "intro", length = 200)
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id")
    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    public List<ClassEntity> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassEntity> classes) {
        this.classes = classes;
    }
}

