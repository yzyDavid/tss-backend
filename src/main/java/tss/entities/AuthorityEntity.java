package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authority", indexes = {
        @Index(name = "uri_index", columnList = "uri", unique = true)
})
public class AuthorityEntity {
    private Short id;
    private String uri;
    private Set<RoleEntity> role = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Column(length = 31, nullable = false, unique = true)
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "authorities")
    public Set<RoleEntity> getRole() {
        return role;
    }

    public void setRole(Set<RoleEntity> role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        } else {
            return (uri.equals(((AuthorityEntity) obj).uri));
        }
    }
}
