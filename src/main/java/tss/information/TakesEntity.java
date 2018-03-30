package tss.information;

import javax.persistence.*;

@Entity
public class TakesEntity {
    private long id;
    private InstructorEntity instructor;
    private UserEntity student;
    private Integer score;
    private Integer year;

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
    public InstructorEntity getInstructor() {
        return instructor;
    }

    public void setInstructor(InstructorEntity instructor) {
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
}
