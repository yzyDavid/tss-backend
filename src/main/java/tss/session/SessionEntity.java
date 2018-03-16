package tss.session;

import javax.persistence.Entity;

@Entity
@Deprecated
public class SessionEntity {
    private Long id;
    private String token;
    private String uid;
}
