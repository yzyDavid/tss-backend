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

    private String uid;
    private String hashedPassword;
    private String salt;
    private String name;
    private String gender;
    private String email;
    private String telephone;
    private String intro;
    /**
     * fileName
     */
    private String photo;
    private DepartmentEntity department;
    private TypeGroupEntity type;
    private MajorClassEntity majorClass;
    private Set<TeachesEntity> teaches = new HashSet<>();
    private Set<TakesEntity> takes = new HashSet<>();
    private Set<AuthorityEntity> dataAccessAuths = new HashSet<>();


    @Column(name = "user_name", length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 2)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String readDepartmentName() {
        if (department != null) {
            return department.getName();
        } else {
            return null;
        }
    }


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "class_id")
    public MajorClassEntity getMajorClass() {
        return majorClass;
    }

    public void setMajorClass(MajorClassEntity majorClass) {
        this.majorClass = majorClass;
    }

    public String readClassName() {
        if (majorClass != null) {
            return majorClass.getName();
        } else {
            return null;
        }
    }

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_authority", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    public Set<AuthorityEntity> getDataAccessAuths() {
        return dataAccessAuths;
    }

    public void setDataAccessAuths(Set<AuthorityEntity> dataAccessAuths) {
        this.dataAccessAuths = dataAccessAuths;
    }

    public void addDataAccessAuth(AuthorityEntity dataAccessAuth) {
        this.dataAccessAuths.add(dataAccessAuth);
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "group_id")
    public TypeGroupEntity getType() {
        return type;
    }

    public void setType(TypeGroupEntity type) {
        this.type = type;
    }

    public String readTypeName() {
        if (type != null) {
            return type.getName();
        } else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        if (uid == null) {
            return super.hashCode();
        } else {
            return uid.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        } else if (uid != null) {
            return (uid.equals(((UserEntity) obj).uid));
        } else {
            return super.equals(obj);
        }
    }

}
