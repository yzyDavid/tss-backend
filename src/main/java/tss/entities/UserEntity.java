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
    @Id
    @Column(name = "user_id", length = 10)
    private String uid;

    @Column(name = "user_name")
    private String name;

    @Column(name = "hashed_pwd", length = 44)
    private String hashedPassword;

    @Column(length = 24)
    private String salt;

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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "group_id")
    private TypeGroupEntity typeGroup;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private List<ClassEntity> classesTeaching = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", orphanRemoval = true)
    private List<ClassRegistrationEntity> classRegistrations = new ArrayList<>();


    // Getter and setter.

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

    public TypeGroupEntity getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(TypeGroupEntity typeGroup) {
        this.typeGroup = typeGroup;
    }

    public String readTypeName() {
        if (typeGroup != null) {
            return typeGroup.getName();
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


    public void addClassTeaching(ClassEntity classEntity) {
        classesTeaching.add(classEntity);
        classEntity.setTeacher(this);
    }

    public void addClassRegistration(ClassRegistrationEntity classRegistrationEntity) {
        classRegistrations.add(classRegistrationEntity);
        classRegistrationEntity.setStudent(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass()) || uid == null) {
            return false;
        } else {
            return (uid.equals(((UserEntity) obj).uid));
        }
    }
}
