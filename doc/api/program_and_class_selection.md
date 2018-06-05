Program and class selection APIs

**注意：json中不特别注明的类型都是String类型**

### 1. 培养方案制定功能

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

|  方法  |       uri       |           请求参数(json)           |                        返回参数(json)                        |           说明           |
| :----: | :-------------: | :--------------------------------: | :----------------------------------------------------------: | :----------------------: |
|  PUT   | /program/course | cid<br>pid<br>uid<br>type(Integer) |                            status                            |   在培养方案中添加课程   |
| DELETE | /program/course |             cid<br>pid             |                status<br>cid<br>cname<br>uid                 |   在培养方案中删除课程   |
|  GET   | /program/status |                 无                 | **List of:**<br>courseId<br>courseName<br>credit(Float)<br>type<br>status | 查看培养方案中课程的状态 |

### 2. 选课相关（学生）

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
- ​

url请求参数举例。如findByboth: 

| 方法 |                uri                |                     请求参数(json或url)                      |                        返回参数(json)                        |                         说明                         |
| :--: | :-------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :--------------------------------------------------: |
| POST |          /course/search           |                       content: String                       | List:{<br>cid: String<br>name:String<br>credit: Float<br>brief: String<br>} |  根据课程名称，返回一个列表，里面包含课程号、课程名称、学分和简介  |
| POST |         /classes/register         |                  classId: Long                  |                        status:String                         |                         选课                         |
| POST | /classes/search | courseName: String<br>courseId: String<br>teacherName: String | List {<br>id: Long<br> year: Integer<br> semester: enum<br> capacity: Integer<br> numStudent: Integer<br>courseId: String<br>courseName: String<br>teacherName: String<br>timeSlot: String<br>classroom: String<br>selected: Integer<br>} | 按一定的条件搜索可选的课程。判断顺序为：(1)如果courseId不为空则按课程ID搜索，不管课程名称和教师名称；(2)如果courseId为空，则courseName和teacherName必须要有一个不空。selected: 0为未选，1为已选 |

### 3. 选课相关（管理员/老师）

| 方法 |       uri        |                 请求参数(json)                  | 返回参数(json) |             说明              |
| :--: | :--------------: | :---------------------------------------------: | :------------: | :---------------------------: |
| PUT  | /classes/confirm |          uid:String[]<br>classId: Long          | status:String  | 确认选上此课程，uid是学生的id |
| PUT  | /classes/finish  | uid:String[]<br>classId: Long<br>score: Integer | status:String  | 确认完成此课程，uid是学生的id |
| PUT  |  /classes/fail   |         uid: String[]<br>classId: Long          | status:String  |  确认某课已挂，uid是学生的id  |

### 4. 选课结果相关

| 方法 |                   uri                   | 请求参数(json) |                        返回参数(json)                        |     说明     |
| :--: | :-------------------------------------: | :------------: | :----------------------------------------------------------: | :----------: |
| GET  | /classes/get_selected/{year}/{semester} |       -        | List{<br>courseId:String<br>courseName:String<br>credit:Float<br>timeSlot:String<br>teacher:String<br>classroom:String<br>} | 查看选课结果 |
|      |                                         |                |                                                              |              |
|      |                                         |                |                                                              |              |
|      |                                         |                |                                                              |              |

### 