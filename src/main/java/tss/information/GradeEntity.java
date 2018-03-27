package tss.information;

import javax.persistence.Entity;

@Entity
public class GradeEntity {
    private long courseID;
    private long studentID;
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if(score >= 0 && score <= 100)
        this.score = score;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }
}
