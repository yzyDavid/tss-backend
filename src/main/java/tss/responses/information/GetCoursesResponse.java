package tss.responses.information;

import tss.entities.CourseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetCoursesResponse {
    private final List<CourseInfo> courses;

    class CourseInfo {
        private String cid;
        private String name;
        private Float credit;
        private String brief;

        CourseInfo() {
        }

        CourseInfo(String cid, String name, Float credit, String brief) {
            this.cid = cid;
            this.name = name;
            this.credit = credit;
            this.brief = brief;
        }

        CourseInfo(CourseEntity course) {
            this.cid = course.getId();
            this.name = course.getName();
            this.credit = course.getCredit();
            this.brief = course.getIntro();
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCredit(Float credit) {
            this.credit = credit;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getCid() {
            return cid;
        }

        public String getName() {
            return name;
        }

        public Float getCredit() {
            return credit;
        }

        public String getBrief() {
            return brief;
        }
    }

    public GetCoursesResponse(List<CourseEntity> courses) {
        this.courses = new ArrayList<>();
        for (CourseEntity clazz : courses) {
            this.courses.add(new CourseInfo(clazz));
        }
    }

    public List<CourseInfo> getCourses() {
        return this.courses;
    }
}
