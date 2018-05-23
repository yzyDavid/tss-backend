package tss.entities.bbs;


import tss.entities.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bbsreply")
public class BbsReplyEntity {
    private long id;
    private UserEntity author;
    private BbsTopicEntity belongedTopic;
    private Integer index;
    private Date time;
    private String content;
    private Integer quoteIndex;

    public BbsReplyEntity(UserEntity author, BbsTopicEntity belongedTopic){
        this.author = author;
        this.belongedTopic = belongedTopic;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "user_id")
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "bbstopic_id")
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

    @Column(name = "floor_index")
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getQuoteIndex() {
        return quoteIndex;
    }

    public void setQuoteIndex(Integer quoteIndex) {
        this.quoteIndex = quoteIndex;
    }
}
