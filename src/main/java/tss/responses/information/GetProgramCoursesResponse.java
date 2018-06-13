package tss.responses.information;

import tss.entities.*;

import java.util.ArrayList;
import java.util.List;

public class GetProgramCoursesResponse {
    private String status;
    private List<CourseInfo> courses;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    class CourseInfo {
        private String courseId;  // courseId
        private String courseName;
        private Float credit;
        private CourseTypeEnum type;
        private ClassStatusEnum status;

        public CourseInfo(String courseId, String courseName, Float credit, CourseTypeEnum type, ClassStatusEnum status) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.credit = credit;
            this.type = type;
            this.status = status;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public Float getCredit() {
            return credit;
        }

        public void setCredit(Float credit) {
            this.credit = credit;
        }

        public CourseTypeEnum getType() {
            return type;
        }

        public void setType(CourseTypeEnum type) {
            this.type = type;
        }

        public ClassStatusEnum getStatus() {
            return status;
        }

        public void setStatus(ClassStatusEnum status) {
            this.status = status;
        }

        CourseInfo(CourseEntity course, CourseTypeEnum type, ClassStatusEnum status) {
            courseId = course.getId();
            courseName = course.getName();
            credit = course.getCredit();
            this.status = status;
            this.type = type;
        }
    }

    public List<CourseInfo> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseInfo> courses) {
        this.courses = courses;
    }

    public GetProgramCoursesResponse(String s, List<CourseEntity> courses, List<CourseTypeEnum> types, List<ClassStatusEnum> status) {
        this.status = s;
        this.courses = new ArrayList<>();
        for (int i=0; i<courses.size(); i++) {
            this.courses.add(new CourseInfo(courses.get(i), types.get(i), status.get(i)));
        }
    }

}
