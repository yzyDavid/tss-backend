package tss.responses.information;

import tss.entities.SelectionTimeEntity;
import java.util.List;

public class GetSelectionTimeResponse {

    private final List<SelectionTimeEntity> timeList;
/*
    class SelectionTimeInfo {
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

        public ClassInfo(Long id, Integer year, SemesterEnum semester, Integer capacity,
                         Integer numStudent, String courseId, String courseName, String teacherName,
                         String timeSlot, String classroom) {
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

        ClassInfo(ClassEntity clazz) {
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
        }
    }

    public GetClassesResponse(List<ClassEntity> classes) {
        this.classes = new ArrayList<>();
        for (ClassEntity clazz : classes)
            this.classes.add(new GetClassesResponse.ClassInfo(clazz));
    }

    public List<GetClassesResponse.ClassInfo> getClasses() {
        return this.classes;
    }
    */

    public GetSelectionTimeResponse(List<SelectionTimeEntity> timeList) {
        this.timeList = timeList;
    }

    public List<SelectionTimeEntity> getTimeList() {
        return timeList;
    }
}
