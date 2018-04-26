package tss.entities;

import sun.misc.Cleaner;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yzy
 * <p>
 * TODO: index
 */
@Entity
@Table(
        name = "user",
        indexes = {
                @Index(name = "user_name_index", columnList = "user_name")
        }
)
public class UserEntity {
    public static final int TYPE_MANAGER = 0;
    public static final int TYPE_TEACHER = 1;
    public static final int TYPE_TA = 2;
    public static final int TYPE_STUDENT = 3;
    public static final int TYPE_NUM = 3;

    @Id
    @Column(name = "user_id", length = 10)
    private String uid;

    @Column(name = "user_name")
    private String name;

    @Column(name = "hashed_pwd", length = 44)
    private String hashedPassword;

    @Column(length = 24)
    private String salt;

    private Integer type;

    @Column(length = 31)
    private String email;

    @Column(length = 16)
    private String telephone;

    private String intro;

    /**
     * fileName
     */
    @Column(length = 10)
    private String photo;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private List<ClassEntity> classesTeaching = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", orphanRemoval = true)
    private List<ClassRegistrationEntity> classRegistrations = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<RoleEntity> roles = new HashSet<>();


    // Getter and setter.

    public static int getTypeManager() {
        return TYPE_MANAGER;
    }

    public static int getTypeTeacher() {
        return TYPE_TEACHER;
    }

    public static int getTypeTa() {
        return TYPE_TA;
    }

    public static int getTypeStudent() {
        return TYPE_STUDENT;
    }

    public static int getTypeNum() {
        return TYPE_NUM;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public List<ClassEntity> getClassesTeaching() {
        return classesTeaching;
    }

    public List<ClassRegistrationEntity> getClassRegistrations() {
        return classRegistrations;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }


    // Utility methods.

    public boolean haveAuthority(String uri) {
        for (RoleEntity role : roles) {
            for (AuthorityEntity authority : role.getAuthorities()) {
                if (authority.getUri().equals(uri)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addClassTeaching(ClassEntity classEntity) {
        classesTeaching.add(classEntity);
        classEntity.setTeacher(this);
    }

    public void addClassRegistration(ClassRegistrationEntity classRegistrationEntity) {
        classRegistrations.add(classRegistrationEntity);
        classRegistrationEntity.setStudent(this);
    }
}
