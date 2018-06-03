package tss.responses.information;

import tss.entities.ClassEntity;
import tss.entities.SemesterEnum;
import tss.entities.TimeSlotEntity;

import java.util.ArrayList;
import java.util.List;

public class GetSelectedClassesResponse {
    private List<ClassSelectedInfo> classes;

    class ClassSelectedInfo {
        //private Integer year;
        //private SemesterEnum semester;
        private String courseId;  // courseId
        private String courseName;
        private Float credit;
        private String timeSlot;
        private String teacher;
        private String classroom;

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

        public String getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(String timeSlot) {
            this.timeSlot = timeSlot;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getClassroom() {
            return classroom;
        }

        public void setClassroom(String classroom) {
            this.classroom = classroom;
        }

        public ClassSelectedInfo(String courseId, String courseName, Float credit, String timeSlot, String teacher, String classroom) {

            this.courseId = courseId;
            this.courseName = courseName;
            this.credit = credit;
            this.timeSlot = timeSlot;
            this.teacher = teacher;
            this.classroom = classroom;
        }

        ClassSelectedInfo(ClassEntity clazz) {
            courseId = clazz.getCourse().getId();
            courseName = clazz.getCourse().getName();
            teacher = clazz.getTeacher().getName();
            timeSlot = "";
            classroom = "";
            List<TimeSlotEntity> ts = clazz.getTimeSlots();
            Integer size = ts.size();
            for (TimeSlotEntity t : ts) {
                timeSlot = timeSlot.concat(t.getType().toString());
                classroom = classroom.concat(t.getClassroom().getName());
                if (size > 1) {
                    timeSlot = timeSlot.concat(",");
                    classroom = classroom.concat(",");
                    size--;
                }
            }
        }
    }

    public GetSelectedClassesResponse(List<ClassEntity> classes) {
        this.classes = new ArrayList<>();
        for (ClassEntity clazz : classes)
            this.classes.add(new ClassSelectedInfo(clazz));
    }

    public List<ClassSelectedInfo> getClasses() {
        return this.classes;
    }
}
