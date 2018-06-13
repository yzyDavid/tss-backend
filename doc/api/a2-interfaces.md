## 教室管理

### 校园

- [x] 新建校园 `POST /campuses` => `Campus`

- [x] 列出校园 `GET /campuses` => `List<Campus>`

- [x] 获取某个校园 `GET /campuses/{campusId}` => `Campus`

- [x] 删除某个校园 `DELETE /campuses/{campusId}` => `void`

- [x] 更新某个校园 `PATCH /campuses/{campusId}` => `void`

- [x] 响应体格式

  ```java
  Campus {
      Integer id;
      String name;
  }
  ```

- [x] 请求体格式

  ```java
  Campus {
      String name;
  }
  ```

### 建筑

- [x] 在某个校园中新建建筑 `POST /campuses/{campusId}/buildings` => `Building`

- [x] 列出某个校园中的建筑 `GET /campuses/{campusId}/buildings` => `List<Building>`

- [x] 获取某个建筑 `GET /buildings/{buildingId}` => `Building`

- [x] 删除某个建筑 `DELETE /buildings/{buildingId}` => `void`

- [x] 更新某个建筑 `PATCH /buildings/{buildingId}` => `void`

- [x] 响应体格式

  ```java
  Building {
      Integer id;
      String name;
      Integer campusId;
      String campusName;
  }
  ```

- [x] 请求体格式

  ```java
  Building {
      String name;
  }
  ```

### 教室

- [x] 在某个建筑中新建教室 `POST /buildings/{buildingId}/classrooms` => `Classroom`

- [x] 列出某个建筑中的教室 `GET /buildings/{buildingId}/classrooms` => `List<Classroom>`

- [x] 获取某个教室 `GET /classrooms/{classroomId}` => ``Classroom``

- [x] 删除某个教室 `DELETE /classrooms/{classroomId}` => `void`

- [x] 更新某个教室 `PATCH /classrooms/{classroomId}` => `void`

- [x] 响应体格式

  ```java
  Classroom {
  	Integer id;
  	String name;
      Integer capacity;
      Integer buildingId;
      String buildingName;
  }
  ```

- [x] 请求体格式

  ```java
  Classroom {
  	String name;
      Integer capacity;
  }
  ```

## 课程班

### 课程班

- [x] 在某个课程中新建课程班 `POST /courses/{courseId}/classes` => 

- [x] 列出某个课程的课程班 `GET /courses/{courseId}/classes` => `List<Clazz>`

- [x] 获取某个课程班 `GET /classes/{classId}` => `Clazz`

- [x] 删除某个课程班 `DELETE /classes/{classId}` => `void`

- [x] 按「名字包含 & 学年 & 学期」查询课程 `GET /classes/search/find-by-course-name-containing-and-year-and-semester{?courseName,year,semester}` => `List<Clazz>`

- [x] 响应体格式

  ```java
  Clazz {
      Long id;
      Integer year;
      SemesterEnum semester;
      Integer capacity;
      Integer numStudent;
      
      String teacherId;
      
      String courseId;
      String courseName;
      Float courseCredit;
      Integer numLessonsEachWeek;
      String courseIntro;
      
      List<TimeSlot> arrangements;
      Integer numLessonsLeft;
  }
  ```

- [x] 请求体格式

  ```java
  Clazz {
      Integer year;
      SemesterEnum semester;
      Integer capacity;
      Integer numStudent;
      String teacherId;
  }
  ```
  SemesterEnum 用枚举值的名字表示，有两个可能的值，`FIRST` 和 `SECOND`。

## 排课与课表

### 时间槽格式

- [x] 列出时间槽格式 `GET /time-slot-types` => `List<TimeSlotType>`

- [x] 响应体格式

  ```java
  TimeSlotType {
  	int dayOfWeek;
      int start;
      int end;
      int size;
  }
  ```

### 教师

- [x] 获取教师课表 `GET /teachers/{userId}/classes{?year,semester}` => `List<Clazz>`


### 教室

- [x] 获取教室课表 `GET /classrooms/{classroomId}/time-slots` => `List<TimeSLot`

- [x] 给某个教室的某个时间槽排一个课程班 `PUT /classrooms/{classroomId}/time-slots/{timeSlotType}/clazz{?classId}` => `void`

- [x] 删除某个教室的某个时间槽排的课程班 `DELETE /classrooms/{classroomId}/time-slots/{timeSlotType}/clazz` => `void`

- [x] 响应体格式

  ```java
  TimeSlot {
      Long id;
      String typeName;
      Long classId;
      String courseName;
      Integer classroomId;
      String buildingName;
      String campusName;
  }
  ```

### 排课

- [x] 设置当前排课学年和学期 `PUT /current-year-semester-of-arrangement` => `void`

- [x] 获取当前排课学年和学期 `GET /current-year-semester-of-arrangement` => `YearSemester`

- [x] 自动排课 `PUT /auto-arrangement{?year,semester}` => `AutoArrangementResult`

- [x] 返回体 & 响应体格式

  ```java
  YearSemester {
  	int year;
  	SemesterEnum semester;
  }
  ```

- [x] 返回体格式

  ```java
  AutoArrangementResult {
  	Integer numFinished;
  	List<Clazz> unfinishedClasses;
  }
  ```

