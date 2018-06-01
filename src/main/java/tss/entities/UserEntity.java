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

    @Column(name = "hashed_pwd", length = 44)
    private String hashedPassword;

    @Column(length = 24)
    private String salt;

    @Column(name = "user_name", length = 31)
    private String name;

    @Column(length = 3)
    private String gender;

    private String email;

    @Column(length = 16)
    private String telephone;

    private String intro;

    private Integer year;

    /**
     * fileName
     */
    @Column(length = 10)
    private String photo;

    @ManyToOne()
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private TypeGroupEntity type;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "class_id")
    private MajorClassEntity majorClass;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_authority", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private Set<AuthorityEntity> dataAccessAuths = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private List<ClassEntity> classesTeaching = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", orphanRemoval = true)
    private List<ClassRegistrationEntity> classRegistrations = new ArrayList<>();


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public String readDepartmentName() {
        if (department != null) {
            return department.getName();
        } else {
            return null;
        }
    }

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

    public Set<AuthorityEntity> getDataAccessAuths() {
        return dataAccessAuths;
    }

    public void setDataAccessAuths(Set<AuthorityEntity> dataAccessAuths) {
        this.dataAccessAuths = dataAccessAuths;
    }

    public void addDataAccessAuth(AuthorityEntity dataAccessAuth) {
        this.dataAccessAuths.add(dataAccessAuth);
    }


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

    public List<ClassEntity> getClassesTeaching() {
        return classesTeaching;
    }

    public void setClassesTeaching(List<ClassEntity> classesTeaching) {
        this.classesTeaching = classesTeaching;
    }

    public void addClassTeaching(ClassEntity classEntity) {
        classesTeaching.add(classEntity);
        classEntity.setTeacher(this);
    }

    public List<ClassRegistrationEntity> getClassRegistrations() {
        return classRegistrations;
    }

    public void setClassRegistrations(List<ClassRegistrationEntity> classRegistrations) {
        this.classRegistrations = classRegistrations;
    }

    public void addClassRegistration(ClassRegistrationEntity classRegistrationEntity) {
        classRegistrations.add(classRegistrationEntity);
        classRegistrationEntity.setStudent(this);
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
