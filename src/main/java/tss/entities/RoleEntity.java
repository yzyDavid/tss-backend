package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role", indexes = {
        @Index(name = "role_name_index", columnList = "role_name", unique = true)
})
public class RoleEntity {
    private Short id;
    private String name;
    private Set<AuthorityEntity> authorities = new HashSet<>();
    private TypeGroupEntity typeGroup;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Column(name = "role_name", length = 31, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    public Set<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(AuthorityEntity authority) {
        authorities.add(authority);
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "group_id")
    public TypeGroupEntity getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(TypeGroupEntity typeGroup) {
        this.typeGroup = typeGroup;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        } else {
            return (name.equals(((RoleEntity) obj).name));
        }
    }
}
