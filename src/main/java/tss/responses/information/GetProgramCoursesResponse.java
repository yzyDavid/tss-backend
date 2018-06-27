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
        private String type;  //CourseTypeEnum type;
        private String status; // ClassStatusEnum status;

        String transferCourseType(CourseTypeEnum type) {
            switch (type) {
                case COMPULSORY: return "必修";
                case PUBLIC: return "公选";
                case SELECTIVE: return "选修";
            }
            return "";
        }

        String transferClassStatus(ClassStatusEnum type) {
            switch (type) {
                case NOT_IN_PROGRAM: return "不在培养方案中";
                case SELECTED: return "已选课";
                case NOT_SELECTED: return "在培养方案中，未选课";
                case FAILED: return "不及格";
                case FINISHED: return "通过";
            }
            return "";
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        CourseInfo(CourseEntity course, CourseTypeEnum type, ClassStatusEnum status) {
            courseId = course.getId();
            courseName = course.getName();
            credit = course.getCredit();
            this.type = transferCourseType(type);
            this.status = transferClassStatus(status);
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
        for (int i = 0; i < courses.size(); i++) {
            this.courses.add(new CourseInfo(courses.get(i), types.get(i), status.get(i)));
        }
    }

}
