package tss.entities.bbs;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bbssection")
public class BbsSectionEntity {
    private long id;
    private String name;
    public static int usrNum = 0;
    private Set<BbsTopicEntity> topics;
    private String notice;

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
        BbsSectionEntity.usrNum = usrNum;
    }

    @Column(name = "section_topic")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "belongedSection")
    public Set<BbsTopicEntity> getTopics() {
        return topics;
    }

    public void setTopics(Set<BbsTopicEntity> topics) {
        this.topics = topics;
    }

    @Column(name = "section_notice")
    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

}
