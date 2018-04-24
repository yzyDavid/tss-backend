package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bbs_section")
public class BbsSectionEntity {
    private long id;
    private String name;
    private Integer usrNum = 0;
    private Set<TeachesEntity> teaches = new HashSet<>();
    private Set<BbsTopicEntity> topics = new HashSet<>();

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

    @Column(name = "section_teaches")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    public Set<TeachesEntity> getTeaches() {
        return teaches;
    }

    public void setTeaches(Set<TeachesEntity> teaches) {
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
