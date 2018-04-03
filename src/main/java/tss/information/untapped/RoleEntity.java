package tss.information.untapped;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class RoleEntity {
    private short id;
    private String name;
    private Set<AuthorityEntity> authorities = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "role_authority", joinColumns = {@JoinColumn(name ="role_id" )}, inverseJoinColumns = { @JoinColumn(name = "authority_id") })
    public Set<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }
}
