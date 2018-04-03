package tss.information;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "takes")
public class TakesEntity {
    private long id;
    private TeachesEntity instructor;
    private UserEntity student;
    private Integer score;
    private Integer year;
    private Set<SectionEntity> sections;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        if(score >= 0 && score <= 100)
        this.score = score;
    }

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "instructor_id")
    public TeachesEntity getInstructor() {
        return instructor;
    }

    public void setInstructor(TeachesEntity instructor) {
        this.instructor = instructor;
    }


    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "student_id")
    public UserEntity getStudent() {
        return student;
    }

    public void setStudent(UserEntity student) {
        this.student = student;
    }

    @Column(name = "year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "takes")
    public Set<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionEntity> sections) {
        this.sections = sections;
    }
}
