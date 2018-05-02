package tss.entities;

import javax.persistence.*;
import java.util.*;

/**
 * @author yzy
 * <p>
 * TODO: index
 */
@Entity
@Table(name = "user", indexes = {
        @Index(name = "user_name_index", columnList = "user_name")
})
public class UserEntity {
    public static final int TYPE_SYS_ADMIN = 0;
    public static final int TYPE_TCH_ADMIN = 1;
    public static final int TYPE_TEACHER = 2;
    public static final int TYPE_STUDENT = 3;
    public static final int TYPE_NUM = 4;
    public static final String[] TYPES = {"System Administrator", "Teaching Administrator", "Teacher", "Student"};
    public static Map<Integer, List<String>> ROLE_ALLOC = new HashMap<Integer, List<String>>(){{
        put(0, new ArrayList<>());
        put(1, new ArrayList<>());
        put(2, new ArrayList<>());
        put(3, new ArrayList<>());
    }};



    private String uid;
    private String name;
    private String hashedPassword;
    private String salt;
    private Integer type = TYPE_STUDENT;
    private String email;
    private String telephone;
    private String intro;
    /**
     * fileName
     */
    private String photo;
    private DepartmentEntity department;
    private Set<TeachesEntity> teaches = new HashSet<>();
    private Set<TakesEntity> takes = new HashSet<>();
    private Set<RoleEntity> roles = new HashSet<>();


    @Column(name = "user_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "user_id", length = 10)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Column(name = "hashed_pwd", length = 44)
    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Column(length = 24)
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(length = 16)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(length = 31)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Column(length = 10)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    public Set<TakesEntity> getTakes() {
        return takes;
    }

    public void setTakes(Set<TakesEntity> takes) {
        this.takes = takes;
    }


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    public Set<TeachesEntity> getTeaches() {
        return teaches;
    }

    public void setTeaches(Set<TeachesEntity> teaches) {
        this.teaches = teaches;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "department_id")
    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public void addRole(RoleEntity role) {
        roles.add(role);
    }

    @Override
    public int hashCode() {
        if(uid == null) {
            return super.hashCode();
        } else {
            return uid.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(this.getClass()) || uid == null) {
            return false;
        } else {
            return (uid.equals(((UserEntity)obj).uid));
        }
    }

}
