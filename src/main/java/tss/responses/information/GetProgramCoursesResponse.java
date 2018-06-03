package tss.responses.information;

import tss.entities.*;

import java.util.ArrayList;
import java.util.List;

public class GetProgramCoursesResponse {
    private List<CourseInfo> courses;

    class CourseInfo {
        private String courseId;  // courseId
        private String courseName;
        private Float credit;
        private Integer type;

        public CourseInfo(String courseId, String courseName, Float credit, Integer type) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.credit = credit;
            this.type = type;
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

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        CourseInfo(CourseEntity course, Integer type) {
            courseId = course.getId();
            courseName = course.getName();
            credit = course.getCredit();
            this.type = type;
        }
    }

    public List<CourseInfo> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseInfo> courses) {
        this.courses = courses;
    }

    public GetProgramCoursesResponse(List<CourseEntity> courses, List<Integer> types) {
        this.courses = new ArrayList<>();
        for (int i=0; i<courses.size(); i++) {
            this.courses.add(new CourseInfo(courses.get(i), types.get(i)));
        }
    }

}
