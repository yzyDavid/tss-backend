package tss.responses.information;

import org.apache.tomcat.util.buf.StringUtils;
import org.jetbrains.annotations.Nls;
import tss.entities.ClassEntity;
import tss.entities.ClassroomEntity;
import tss.entities.SemesterEnum;
import tss.entities.TimeSlotEntity;
import tss.models.TimeSlotTypeEnum;

// import tss.entities.ClassInfo;
import java.util.ArrayList;
import java.util.List;

public class GetClassesResponse {
    private String status;
    private final List<ClassInfo> classes;

    class ClassInfo {
        private Long id;
        private String semester;
        private Integer capacity;
        private Integer numStudent;
        private Float credit;
        private String intro;
        private String courseId;
        private String courseName;
        private String teacherName;
        private String timeSlot;
        private String classroom;
        private String status;

        public ClassInfo() {
        }

        public ClassInfo(Long id, String semester, Integer capacity, Integer numStudent,
                         Float credit, String intro, String courseId, String courseName, String teacherName,
                         String timeSlot, String classroom, String status) {
            this.id = id;
            this.semester = semester;
            this.capacity = capacity;
            this.numStudent = numStudent;
            this.credit = credit;
            this.intro = intro;
            this.courseId = courseId;
            this.courseName = courseName;
            this.teacherName = teacherName;
            this.timeSlot = timeSlot;
            this.classroom = classroom;
            this.status = status;
        }

        public Float getCredit() {
            return credit;
        }

        public void setCredit(Float credit) {
            this.credit = credit;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
        
        String transferTimeSlot(TimeSlotTypeEnum timeSlotTypeEnum) {
            String res = "";
            switch (timeSlotTypeEnum.getDayOfWeek()) {
                case 1: res = "周一"; break;
                case 2: res = "周二"; break;
                case 3: res = "周三"; break;
                case 4: res = "周四"; break;
                case 5: res = "周五"; break;
                case 6: res = "周六"; break;
                case 7: res = "周日"; break;
            }
            res += "第"+timeSlotTypeEnum.getStart()+"至"+timeSlotTypeEnum.getEnd()+"节";
            return res;
        }

        ClassInfo(ClassEntity clazz, Boolean selected, Integer numOfStudents) {
            id = clazz.getId();
            semester = clazz.getYear().toString() + ((clazz.getSemester() == SemesterEnum.FIRST) ? "秋冬" : "春夏");
            capacity = clazz.getCapacity();
            numStudent = numOfStudents;
            credit = clazz.getCourse().getCredit();
            intro = clazz.getCourse().getIntro();
            courseId = clazz.getCourse().getId();
            courseName = clazz.getCourse().getName();
            teacherName = clazz.getTeacher().getName();
            timeSlot = "";
            classroom = "";
            List<TimeSlotEntity> ts = clazz.getTimeSlots();
            Integer size = ts.size();
            for (TimeSlotEntity t : ts) {
                timeSlot = timeSlot.concat(transferTimeSlot(t.getType()));
                classroom = classroom.concat(t.getClassroom().getName());
                if (size > 1) {
                    timeSlot = timeSlot.concat(",");
                    classroom = classroom.concat(",");
                    size--;
                }
            }
            status = selected ? "已选" : "未选";
        }
    }

    public GetClassesResponse(String status, List<ClassEntity> classes, List<Boolean> selected, List<Integer> numOfStudents) {
        this.status = status;
        this.classes = new ArrayList<>();
        for (int i = 0; i < classes.size(); i++) {
            this.classes.add(new ClassInfo(classes.get(i), selected.get(i), numOfStudents.get(i)));
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ClassInfo> getClasses() {
        return this.classes;
    }
}