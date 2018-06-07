Program and class selection APIs

**注意：json中不特别注明的类型都是String类型**

### 1. 培养方案制定功能

|  方法  |              uri               |           请求参数(json)           |                        返回参数(json)                        |             说明             |
| :----: | :----------------------------: | :--------------------------------: | :----------------------------------------------------------: | :--------------------------: |
|  PUT   |        /program/course         | cid<br>pid<br>uid<br>type(Integer) |                            status                            |       培养方案添加课程       |
| DELETE |        /program/course         |             cid<br>pid             |                status<br>cid<br>cname<br>uid                 |       培养方案删除课程       |
|  GET   |        /program/status         |                 无                 | **List of:**<br>courseId<br>courseName<br>credit(Float)<br>type<br>status |    查看培养方案中课程状态    |
|  GET   |   /program/status/in_program   |                 无                 |                             同上                             | 查看已选进培养方案的课程状态 |
|  GET   | /program/status/not_in_program |                 无                 |                             同上                             | 查看未选进培养方案的课程状态 |

+ 在培养方案中添加课程
  + url: `PUT /program/course`
  + request: `{"cid", "pid", "uid", "type"}`
    + **type**: Integer(0-compulsory, 1-selective, 2-public)
  + response: `{"status"}`
+ 在培养方案中删除课程
  + url: `DELETE /program/course`
  + request: `{"cid", "pid"}`
  + response: `{"status", "cid", "cname", "uid"}`
    + cname-课程名称, uid-用户id
+ 查看培养方案中课程的状态（哪些课程已选，哪些没选，……）
  + url: `GET /program/status`
  + request: 无
  + response: `{"courses:[{"courseId", "courseName", "credit", "type", "status"}, {..}, ...]"}`
    + **credit**: Float
    + **type**: String, 为`COMPULSORY/SELECTIVE/PUBLIC`，表示课程类型
    + **status**: String, 为`NOT_SELECTED/SELECTED/FINISHED/FAILED`，表示课程状态
    + 是一个列表
+ 查看培养方案中已选课程
  + url: `GET /program/status/in_program`
  + 其他与/program/status相同
+ 查看培养方案中未选课程
  + url: `GET /program/status/not_in_program`
  + 其他与/program/status相同

### 2. 选课相关（学生）

|  方法  |                     uri                     | 请求参数(json) |                        返回参数(json)                        |              说明              |
| :----: | :-----------------------------------------: | :------------: | :----------------------------------------------------------: | :----------------------------: |
|  POST  |               /classes/search               | 四选一（见下） | **List of**:<br>id(Long)<br>year(Integer)<br>semester<br>capacity(Integer)<br>numStudent(Integer)<br>courseId<br>courseName<br>teachearName<br>timeSlot<br>classroom |          搜索可选课程          |
|  POST  |              /classes/register              | classId(Long)  |                            status                            |              选课              |
| DELETE |                /classes/drop                | classId(Long)  |                            status                            |              退课              |
|  GET   | /classes/get_selected/<br>{year}/{semester} |       无       | **List of:**<br>courseId<br>courseName<br>credit(Float)<br>timeSlot<br>teacher<br>classroom |            查看课表            |
|  POST  |              /course/get/info               |      cid       | status<br>cid<br>name<br>credit(Float)<br>numLessonsEachWeek(Integer)<br>department<br>intro | 查看课程信息**（是A2组写的）** |

- 按**课程Id，课程名称，教师名称，课程与教师名称**来搜索可选的课程
  - url: `POST /classes/search`
  - request:
    - 按课程id：`{"courseId"}`
    - 按课程名称：`{"courseName"}`
    - 按教师名称：`{"teacherName"}`
    - 按课程和教师名称：`{"courseName", "teacherName"}`
  - response: `{"classes":[{"id", "year", "semester", "capacity", "numStudent", "courseId", "courseName", "teacherName", "timeSlot", "classroom"},{..},..]}`
    - **id**: Long
    - **year, capacity, numStudent**: Integer
    - **semester**: String, `FIRST/SECOND`
- 选课
  - url: `POST /classes/register`
  - request: `{"classId"}`
    - **classId**: Long
  - response: `{"status":"Class registered successfully!"}` （提示信息）
- 课程结束（我怀疑这个不用写，先放放）
  - url: `PUT /classes/finish`
  - request: `{"uid", "classId", "score"}`
    - **classId**: Long
    - **score**: Integer
  - response: `{"status":"Class finished."}`（提示信息）
- 挂科（我怀疑这个也不用写）
  - url: `PUT /classes/fail`
  - request: `{"uid", "classId"}`
    - **classId**: Long
  - response: `{"status":"Class failed successfully."}`
- 补课（这个要写吗？我先写了）
  - url: `POST /classes/complement`
  - request: `{"classId"}`
    - **classId**: Long
  - response: `{"status":"Class filled successfully."}`
- 退课
  - url: `DELETE /classes/drop`
  - request: `{"classId"}`
    - **classId**: Long
  - response: `{"status":"Class dropped."}`
- 查看课表
  - url: `GET /classes/get_selected/{year}/{semester}`
    - semester = FIRST/SECOND
  - request: 无
  - response: `{"classes":[{"courseId", "courseName", "credit", "timeSlot", "teacher", "classroom"}, {..}, ..]}`
    - **credit**: Float

### 3. 管理员对课的操作

| 方法 |           url           | 请求参数(json) | 返回参数(json) |    说明    |
| :--: | :---------------------: | :------------: | :------------: | :--------: |
| POST | /classes/admin_register | classId(Long)  |     status     | 管理员选课 |

+ 管理员加课
  + url: `POST /classes/admin_register`
  + request: `{"classId"}`
    + **classId**: Long
  + response: `{"status":"Class registered by admin successfully!"}`
+ 管理员退课？？？

### 4. 选课时间

**Timestamp=yyyy-MM-dd HH:mm:ss**

|  方法  |             uri              |           请求参数(json)           |                        返回参数(json)                        |                说明                |
| :----: | :--------------------------: | :--------------------------------: | :----------------------------------------------------------: | :--------------------------------: |
|  POST  |   /selection_time/register   | start(Timestamp)<br>end(Timestamp) |                            status                            |            添加选课时间            |
|  POST  |  /selection_time/complement  | start(Timestamp)<br>end(Timestamp) |                            status                            |            添加补选时间            |
|  POST  |     /selection_time/drop     | start(Timestamp)<br>end(Timestamp) |                            status                            |            添加退课时间            |
|  GET   |     /selection_time/show     |                 -                  | **List of:**<br>id(Long)<br>start(Timestamp)<br>end(Timestamp)<br>register<br>drop<br>complement |      显示所有的选/补/退课时间      |
| DELETE |  /selection_time/deleteById  |              id(Long)              |                            status                            |       按id删除选/补/退课时间       |
|  PUT   | /selection_time/modify/start |    id(Long)<br>start(Timestamp)    |                            status                            | 设置某一段选/补/退课时间的开始时间 |
|  PUT   |  /selection_time/modify/end  |    id(Long)<br>start(Timestamp)    |                            status                            | 设置某一段选/补/退课时间的结束时间 |

 GET: /selection_time/show 例子：

![1528225003993](img\001.png)



### 5. 导出功能

权限：管理员或老师

| 方法 |                url                | 请求参数(json) |    返回     |         说明         |
| :--: | :-------------------------------: | :------------: | :---------: | :------------------: |
| GET  |    /excel/download/{courseId}     |       -        | 一个excel表 | 导出一个course的名单 |
| GET  | /excel/download/classes/{classId} |       -        | 一个excel表 | 导出一个class的名单  |





## 后期

#### 对培养方案模块接口的优化

PUT /program/course: 加上@Authorization, 在request中只留下cid

DELETE /program/course: 加上@Authorization, 在request中只留下cid

+ 在确保没有选这门课的情况下才可以删除

#### 对选课/退课的优化

