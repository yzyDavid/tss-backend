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
    private Character semester;
    private String cid;
    private ClassEntity classEntity;

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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "class_id", nullable = false)
    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    @Column(name = "semester")
    public Character getSemester() {
        return semester;
    }

    public void setSemester(Character semester) {
        this.semester = semester;
    }

    @Column(name = "course_id", length = 10, nullable = false)
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        } else {
            return (id == ((TakesEntity) obj).id);
        }
    }
}
