package tss.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ymq
 */
@Entity
@Table(name = "class")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Integer year;

    private String semester;

    private Integer capacity;

    @Column(name = "num_student")
    private Integer numStudent = 0;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id")
    private UserEntity teacher;

    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArrangementEntity> arrangements = new ArrayList<>();

    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassRegistrationEntity> classRegistrations = new ArrayList<>();

    public ClassEntity() {
    }

    public ClassEntity(Integer year, String semester, Integer capacity, Integer numStudent, CourseEntity course, UserEntity teacher) {
        this.year = year;
        this.semester = semester;
        this.capacity = capacity;
        this.numStudent = numStudent;
        this.course = course;
        this.teacher = teacher;
    }


    // Getter and setter.

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getNumStudent() {
        return numStudent;
    }

    public void setNumStudent(Integer numStudent) {
        this.numStudent = numStudent;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public UserEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(UserEntity teacher) {
        this.teacher = teacher;
    }

    public List<ArrangementEntity> getArrangements() {
        return arrangements;
    }

    public List<ClassRegistrationEntity> getClassRegistrations() {
        return classRegistrations;
    }


    // Utility methods.

    public void addArrangements(ArrangementEntity arrangementEntity) {
        arrangements.add(arrangementEntity);
        arrangementEntity.setClazz(this);
    }

    public void addClassRegistration(ClassRegistrationEntity classRegistrationEntity) {
        classRegistrations.add(classRegistrationEntity);
        classRegistrationEntity.setClazz(this);
    }
}
