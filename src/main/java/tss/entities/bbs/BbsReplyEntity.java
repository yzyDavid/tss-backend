package tss.entities.bbs;

import tss.entities.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bbs_reply")
public class BbsReplyEntity {
    private long id;
    private UserEntity author;
    private BbsTopicEntity belongedTopic;
    private Date time;
    private String content;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "reply_author")
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @Column(name = "topic_belonged")
    public BbsTopicEntity getBelongedTopic() {
        return belongedTopic;
    }

    public void setBelongedTopic(BbsTopicEntity belongedTopic) {
        this.belongedTopic = belongedTopic;
    }

    @Column(name = "reply_time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "reply_content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
