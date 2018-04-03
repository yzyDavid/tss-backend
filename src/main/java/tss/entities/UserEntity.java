package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yzy
 * <p>
 * TODO: index
 */
@Entity
@Table(name = "user")
public class UserEntity {
    public static final int TYPE_MANAGER = 0;
    public static final int TYPE_TEACHER = 1;
    public static final int TYPE_TA = 2;
    public static final int TYPE_STUDENT = 3;
    public static final int TYPE_NUM = 3;

    public static final int MODIFY_OTHERS_PWD = 0;
    public static final int MODIFY_OTHERS_INFO = 1;
    public static final int ADD_USER = 2;
    public static final int DELETE_USER = 3;
    public static final int ADD_COURSE = 4;
    public static final int ADD_TEACHER = 5;
    public static final int ADD_TA = 6;
    public static final int ADD_STUDENT = 7;
    // all kinds of operation

    //private static Map<Integer, Set<Integer>> typeRights;

    private String uid;

    private String name;

    private String hashedPassword;

    private String salt;

    private Integer type;

    private String email;

    private String telephone;

    private String intro;

    /**
     * fileName
     */
    private String photo;

    //private Set<Integer> rights = new HashSet<>();

    private Set<TeachesEntity> instructors = new HashSet<>();

    private Set<TakesEntity> takes = new HashSet<>();

    //TODO : photo, rights, department etc.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "course_id", length = 10)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
        if (0 < type && type < TYPE_NUM) {
            /*for(Integer right : typeRights.get(this.type))
                rights.remove(right);*/
            this.type = type;
            /*for(Integer right : typeRights.get(this.type))
                rights.add(right); //modify rights*/
        }
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /*public Set<Integer> getRights() {
        return rights;
    }

    public void setRights(Set<Integer> rights) {
        this.rights = rights;
    }

    public void addRight(int right) {
        this.rights.add(right);
    }

    public void addRights(Set<Integer> rights) {
        for(Integer right : rights) {
            this.rights.add(right);
        }
    }

    public boolean hasRight(int right) {
        return this.rights.contains(right);
    }*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    public Set<TakesEntity> getTakes() {
        return takes;
    }

    public void setTakes(Set<TakesEntity> takes) {
        this.takes = takes;
    }

    public void addTake(TakesEntity take) {
        takes.add(take);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    public Set<TeachesEntity> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<TeachesEntity> instructors) {
        this.instructors = instructors;
    }

    public void addInstructors(TeachesEntity instructor) {
        instructors.add(instructor);
    }
}
