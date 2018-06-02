Program and class selection APIs

### 1. 培养方案制定功能

|  方法  |       uri       |                        请求参数(json)                        |                      返回参数(json)                       |          说明          |
| :----: | :-------------: | :----------------------------------------------------------: | :-------------------------------------------------------: | :--------------------: |
|  PUT   |       ???       |                   uid:String[] (required)                    |                      status: String                       | 为某个用户添加培养方案 |
|  PUT   | /program/course | cid:String[] (required)<br>pid:String[] (required)<br>uid:String[] (required)<br>type:Integer[] (required) |                       status:String                       |  在培养方案中添加课程  |
| DELETE | /program/course |     cid:String[] (required) <br>pid:String[] (required)      | status:String<br>cid:String<br>cname:String<br>uid:String |  在培养方案中删除课程  |

### 2. 地

url请求参数举例：如findByboth: /classes/search/findByBoth

| 方法 |                uri                |                     请求参数(json或url)                      |                        返回参数(json)                        |                         说明                         |    身份     |
| :--: | :-------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :--------------------------------------------------: | :---------: |
| GET  | /classes/search/ findByCourseName | **url:**<br>name<br>year(not required)<br>semester(not required) | List {<br>id: Long<br> year: Integer<br> semester: enum<br> capacity: Integer<br> numStudent: Integer<br>courseId: String<br>courseName: String<br>teacherName: String<br>timeSlot: String<br>classroom: String<br>} |     按课程名称搜索 semester="FIRST" or "SECOND"      |    学生     |
| GET  |  /classes/search/ findByCourseId  | **url:** id<br>year(not required)<br> semester(not required) |                             同上                             |      按课程ID搜索 semester="FIRST" or "SECOND"       |    学生     |
| GET  |  /classes/search/ findByTeacher   | **url:** name<br> year(not required)<br> semester(not required) |                             同上                             |     按老师名称搜索 semester="FIRST" or "SECOND"      |    学生     |
| GET  |    /classes/search/ findByBoth    | **url**: <br>courseName<br> teacherName<br> year(not required)<br> semester(not required) |                             同上                             | 按课程名称和老师一起搜索semester="FIRST" or "SECOND" |    学生     |
| GET  |          /course/search           |                       **url**:<br>name                       | List:{<br>cid: String<br>name:String<br>credit: Float<br>brief: String} |  返回一个列表，里面包含课程号、课程名称、学分和简介  |    学生     |
| POST |         /classes/register         |                  **json:**<br>classId: Long                  |                        status:String                         |                         选课                         |    学生     |
| PUT  |         /classes/confirm          |           **json:**<br>uid:String[] classId: Long            |                        status:String                         |          确认**选上**此课程，uid是学生的id           |   管理员    |
| PUT  |          /classes/finish          |  **json**:<br>uid:String[]<br> classId: Long score: Integer  |                        status:String                         |            确认完成此课程，uid是学生的id             | 管理员/老师 |
| PUT  |          /classes/failed          |       **json:**<br>uid: String[]<br>classId: Long<br>        |                        status: String                        |             确认某课已挂，uid是学生的id              | 管理员/老师 |

### 3. 选课功能