package tss.entities.bbs;


import javax.persistence.*;

@Entity
@Table(name = "bbstake")
public class BbsTakeEntity {
    private String id;
    private String uid;
    private long sid;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "user_id")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Column(name = "section_id")
    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}
