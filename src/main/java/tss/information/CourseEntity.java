package tss.information;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Integer credit;
    private Integer semester;
    private String intro;
    private Integer examBeginTime;
    private Integer examDuration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        if(0 <= semester && semester < 4)
            this.semester = semester;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getExamBeginTime() {
        return examBeginTime;
    }

    public void setExamBeginTime(int examBeginTime) {
        this.examBeginTime = examBeginTime;
    }

    public int getExamduration() {
        return examDuration;
    }

    public void setExamduration(int examDuration) {
        this.examDuration = examDuration;
    }
}

