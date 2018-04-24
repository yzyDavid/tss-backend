package tss.entities.bbs;

import tss.entities.TeachesEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bbssection")
public class BbsSectionEntity {
    private long id;
    private String name;
    public static int usrNum = 0;
    private TeachesEntity teaches;
    private Set<BbsTopicEntity> topics;

    public BbsSectionEntity(TeachesEntity teaches){
        this.teaches = teaches;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "section_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "user_number")
    public Integer getUsrNum() {
        return usrNum;
    }

    public void setUsrNum(Integer usrNum) {
        this.usrNum = usrNum;
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "section_teaches")
    public TeachesEntity getTeaches() {
        return teaches;
    }

    public void setTeaches(TeachesEntity teaches) {
        this.teaches = teaches;
    }

    @Column(name = "section_topic")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "belongedSection")
    public Set<BbsTopicEntity> getTopics() {
        return topics;
    }

    public void setTopics(Set<BbsTopicEntity> topics) {
        this.topics = topics;
    }
}
