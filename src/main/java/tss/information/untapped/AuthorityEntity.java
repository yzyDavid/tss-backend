package tss.information.untapped;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authority")
public class AuthorityEntity {
    private short id;
    private String uri;
    private Set<RoleEntity> role = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Column(length = 32)
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @ManyToMany(mappedBy="authorities")
    public Set<RoleEntity> getRole() {
        return role;
    }

    public void setRole(Set<RoleEntity> role) {
        this.role = role;
    }
}
