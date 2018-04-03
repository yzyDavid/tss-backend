package tss.information;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "instructor")
public class TeachesEntity {
    private long id;
    private UserEntity teacher;
    private Integer capacity;
    private String cid;
    private Set<SectionEntity> sections = new HashSet<>();
    private Set<TakesEntity> takes = new HashSet<>();

        //this.id = new CompositeKeys(user.getUid(), course.getCid());
        public TeachesEntity(UserEntity user, String cid) {
        this.cid = cid;
        this.teacher = user;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }


    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "teacher_id")
    public UserEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(UserEntity teacher) {
        this.teacher = teacher;
    }

    @Column(name = "capacity")
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teaches")
    public Set<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionEntity> sections) {
        this.sections = sections;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teaches")
    public Set<TakesEntity> getTakes() {
        return takes;
    }

    public void setTakes(Set<TakesEntity> takes) {
        this.takes = takes;
    }
}
