package tss.entities.bbs;


import javax.persistence.*;

@Entity
@Table(name = "bbstake")
public class BbsTakeEntity {
    private Integer tid;
    private String uid;
    private long sid;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
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
