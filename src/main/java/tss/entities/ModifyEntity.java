package tss.entities;

import javax.persistence.*;

@Entity
@Table(name="ManageScore")
public class ModifyEntity {
    private long id;
    private String uid;
    private long cid;
    private int score;
    private String reasons;
    private Boolean status;
    private Boolean process;

    public ModifyEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "studentid",length = 10)
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    @Column(name = "classid")
    public long getCid() {
        return cid;
    }
    public void setCid(long cid) {
        this.cid = cid;
    }

    @Column(name="score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Column(name="reasons",length = 255)
    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    @Column(name="status")

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    @Column(name="process")

    public Boolean getProcess() {
        return process;
    }

    public void setProcess(Boolean process) {
        this.process = process;
    }
}
