package tss.session;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author yzy
 */
@Entity
@Table(indexes = {
        @Index(name = "uidIndex", columnList = "uid", unique = true)
})
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uid;

    @GeneratedValue()
    private Timestamp timestamp;

    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
