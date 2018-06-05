package tss.responses.information;

import org.apache.tomcat.util.buf.StringUtils;
import org.jetbrains.annotations.Nls;
import tss.entities.ClassEntity;
import tss.entities.ClassroomEntity;
import tss.entities.SemesterEnum;
import tss.entities.TimeSlotEntity;

// import tss.entities.ClassInfo;
import java.util.ArrayList;
import java.util.List;

public class GetClassesResponse {
    private final List<ClassInfo> classes;

    class ClassInfo {
        private Long id;
        private Integer year;
        private SemesterEnum semester;
        private Integer capacity;
        private Integer numStudent;
        private String courseId;
        private String courseName;
        private String teacherName;
        private String timeSlot;
        private String classroom;
        private String status;

        public ClassInfo() {
        }

        public ClassInfo(Long id, Integer year, SemesterEnum semester, Integer capacity,
                         Integer numStudent, String courseId, String courseName,
                         String teacherName, String timeSlot, String classroom, String status) {
            this.id = id;
            this.year = year;
            this.semester = semester;
            this.capacity = capacity;
            this.numStudent = numStudent;
            this.courseId = courseId;
            this.courseName = courseName;
            this.teacherName = teacherName;
            this.timeSlot = timeSlot;
            this.classroom = classroom;
            this.status = status;
        }

        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }

        public Integer getYear() {
            return year;
        }
        public void setYear(Integer year) {
            this.year = year;
        }

        public SemesterEnum getSemester() {
            return semester;
        }
        public void setSemester(SemesterEnum semester) {
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

        public String getTeacherName() {
            return teacherName;
        }
        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTimeSlot() {
            return timeSlot;
        }
        public void setTimeSlot(String timeSlot) {
            this.timeSlot = timeSlot;
        }

        public String getClassroom() {
            return classroom;
        }
        public void setClassroom(String classroom) {
            this.classroom = classroom;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        ClassInfo(ClassEntity clazz, Boolean selected) {
            id = clazz.getId();
            year = clazz.getYear();
            semester = clazz.getSemester();
            capacity = clazz.getCapacity();
            numStudent = clazz.getNumStudent();
            courseId = clazz.getCourse().getId();
            courseName = clazz.getCourse().getName();
            teacherName = clazz.getTeacher().getName();
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
            status = selected ? "SELECTED" : "NOT_SELECTED";
        }
    }

    public GetClassesResponse(List<ClassEntity> classes, List<Boolean> selected) {
        this.classes = new ArrayList<>();
        for (int i=0; i<classes.size(); i++)
            this.classes.add(new ClassInfo(classes.get(i), selected.get(i)));
    }

    public List<ClassInfo> getClasses() {
        return this.classes;
    }
}