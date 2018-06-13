package tss.entities.bbs;


import tss.entities.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bbsretrieve")
public class BbsRetrieveEntity {
    private long id;
    private UserEntity sender;
    private UserEntity receiver;
    private String content;
    private Date time;
    private String title;
    private boolean isChecked;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "retrieve_content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "retrieve_time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "retrieve_check")
    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        this.isChecked = checked;
    }


    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "retrieve_sender")
    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "retrieve_receiver")
    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    @Column(name = "retrieve_title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
