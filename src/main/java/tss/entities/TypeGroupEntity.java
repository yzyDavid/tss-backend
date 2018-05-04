package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type_group", indexes = {
        @Index(name = "group_name_index", columnList = "group_name", unique = true)
})
public class TypeGroupEntity {
    private Short id;
    private String name;
    private Set<UserEntity> users = new HashSet<>();
    private Set<RoleEntity> roles = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Column(name = "group_name", length = 31)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "typeGroup", fetch = FetchType.EAGER)
    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public void addUser(UserEntity user) {
        this.users.add(user);
    }

    public void deleteUser(UserEntity user) {
        this.users.remove(user);
    }

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "typeGroup", fetch = FetchType.EAGER)
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public void addRole(RoleEntity role) {
        this.roles.add(role);
    }

    public void deleteRole(RoleEntity role) {
        this.roles.remove(role);
    }


}
