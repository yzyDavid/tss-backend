package tss.entities.bbs;

import tss.entities.UserEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "bbstopic")
public class BbsTopicEntity {
    private long id;
    private String name;
    private UserEntity author;
    private BbsSectionEntity belongedSection;
    private Date time;
    private String content;

    private int replyNum = 0;
    private Set<BbsReplyEntity> replies;

    public BbsTopicEntity(UserEntity author, BbsSectionEntity belongedSection){
        this.author = author;
        this.belongedSection = belongedSection;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "topic_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id")
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "bbssection_id")
    public BbsSectionEntity getBelongedSection() {
        return belongedSection;
    }

    public void setBelongedSection(BbsSectionEntity belongedSection) {
        this.belongedSection = belongedSection;
    }

    @Column(name = "topic_time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "topic_content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "reply_number")
    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "belongedTopic")
    public Set<BbsReplyEntity> getReplies() {
        return replies;
    }

    public void setReplies(Set<BbsReplyEntity> replies) {
        this.replies = replies;
    }
}
