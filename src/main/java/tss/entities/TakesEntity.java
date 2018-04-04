package tss.entities;

import javax.persistence.*;

/**
 * @author Mingqi Yi
 * <p>
 * TODO: add hashCode and equals method
 */
@Entity
@Table(name = "takes")
public class TakesEntity {
    private final int SCORE_MAX = 100;

    private long id;
    private UserEntity student;
    private Integer score;
    private Integer year;
    private TeachesEntity teaches;

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
        if (score >= 0 && score <= SCORE_MAX) {
            this.score = score;
        }
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

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "teaches_id")
    public TeachesEntity getTeaches() {
        return teaches;
    }

    public void setTeaches(TeachesEntity teaches) {
        this.teaches = teaches;
    }
}
