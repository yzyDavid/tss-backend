Program and class selection APIs

### 1. 培养方案制定功能

|  方法  |           uri           |                        请求参数(json)                        |                      返回参数(json)                       |          说明          |
| :----: | :---------------------: | :----------------------------------------------------------: | :-------------------------------------------------------: | :--------------------: |
|  PUT   |           ???           |                   uid:String[] (required)                    |                      status: String                       | 为某个用户添加培养方案 |
|  PUT   |     /program/course     | cid:String[] (required)<br>pid:String[] (required)<br>uid:String[] (required)<br>type:Integer[] (required) |                       status:String                       |  在培养方案中添加课程  |
| DELETE |     /program/course     |     cid:String[] (required) <br>pid:String[] (required)      | status:String<br>cid:String<br>cname:String<br>uid:String |  在培养方案中删除课程  |
|  GET   | /program/show_selection |                              无                              |                        List{<br>}                         |                        |

### 2. 选课相关（学生）

url请求参数举例。如findByboth: 

```
不需要学期和年份时：/classes/search/findByBoth?courseName=Data%20Structure,teacherName=Root
需要学期和年份时：
/classes/search/findByBoth?courseName=Data%20Structure,teacherName=Root,year=2017,semester=FIRST
```



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