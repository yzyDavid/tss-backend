## information APIs

### 1. 权限配置

​	当前设计中，权限通过后端的配置文件进行配置，不为前端提供接口。具体配置格式参考`backend/init_date.md`。

### 2. 用户管理

​	需要为系统/教务管理员提供对用户数据库进行增删查改的接口，同时需要为每个用户提供查看和修改基本信息，修改密码，上传图片资源的接口。所有的请求都需要在请求头中添加token。

|  方法  |          uri          |                        请求参数(json)                        |                        返回参数(json)                        |                         说明                         |
| :----: | :-------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :--------------------------------------------------: |
|  PUT   |       /user/add       | uids:String[] (required)<br>passwords:String[] (required)<br>names:String[] (required)<br>genders:string[] (required)<br>type:String (required) | status:String<br>uid:String<br>name:String<br>gender:String<br>type:String |  批量添加用户，失败后返回不合法的用户数据和失败原因  |
| DELETE |     /user/delete      |                   uids:String[] (required)                   |                status:String<br>uids:String[]                |          批量删除用户，返回所有不存在的用户          |
|  POST  |   /user/modify/pwd    | oldPassword:String(required)<br>newPassword:String(required) |          status:String<br>uid:String<br>name:String          | 更改当前用户的密码，成功后返回当前用户的用户名和姓名 |
|  POST  |    /user/reset/pwd    |                     uid:String(required)                     |                        status:String                         |                  重置指定用户的密码                  |
|  POST  | /user/modify/own/info | email:String(optional)<br> telephone:String(optional)<br> intro:String(optional) | status:String<br> uid:String<br>name:String<br> gender:String<br> majorClass:String<br> type:String<br> department:String<br> email:String<br> telephone:String<br> intro:String |   修改当前用户的基本信息，返回修改后用户的所有信息   |
|  POST  |   /user/modify/info   | uid:String(required)<br>name:String(optional)<br>gender:String(optimal)<br>majorClass:String(optimal)<br>type:String(optional)<br>department:String(optional)<br>email:String(optional)<br>telephone:String(optional)<br>intro:String(optional) | status:String<br> uid:String<br>name:String<br> gender:String<br> majorClass:String<br> type:String<br> department:String<br> email:String<br> telephone:String<br> intro:String |   修改指定用户的所有信息，返回修改后用户的所有信息   |
|  POST  |  /user/get/own/info   |                             None                             | status:String<br> uid:String<br>name:String<br> gender:String<br> majorClass:String<br> type:String<br> department:String<br> email:String<br> telephone:String<br> intro:String |                获取当前用户的所有信息                |
|  POST  |    /user/get/info     |                     uid:String(required)                     | status:String<br> uid:String<br>name:String<br> gender:String<br> majorClass:String<br> type:String<br> department:String<br>email:String<br>telephone:String<br>intro:String |                获取指定用户的所有信息                |
|  POST  |      /user/query      | uid:String(optional)<br>name:String(optional)<br>department:String(optional) | status:String<br>uids:String[]<br>names:String[]<br>departments:String[]<br>genders:String[]<br>types:String[]<br>years:Int[] |                     用户模糊查询                     |
|  PUT   |  /user/modify/photo   |                    photo:String(required)                    |                        status:String                         |                   当前用户上传照片                   |
|  GET   |    /user/get/photo    |                             None                             |                status:String<br>photo:String                 |                   当前用户获取照片                   |



### 3. 课程管理

​	需要为系统/教务管理员提供增删课程，遍历课程和修改课程信息的接口。所有的请求都需要在请求头中添加token。

|  方法  |       uri        |                        请求参数(json)                        |                        返回参数(json)                        |        说明        |
| :----: | :--------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------: |
|  PUT   |   /course/add    | cid:String(required)<br>name:String(requierd)<br>credit:Float(optional)<br>numLessonsEachWeek:Int(optional)<br>department:String(optinal) | status:String<br>cid:String<br>name:String<br>credit:Float<br>numLessonsEachWeek:Int<br>department:String |      添加课程      |
| DELETE |  /course/delete  |                     cid:String(required)                     |          status:String<br>cid:String<br>name:String          |      删除课程      |
|  POST  |  /course/modify  | cid:String(required)<br>name:String(optional)<br>credit:Float(optional)<br>numLessonsEachWeek:Int(optional)<br>department:String(optinal)<br>intro:String(optional)<br>year:Int(optional) | status:String<br>cid:String<br>name:String<br>credit:Float<br>numLessonsEachWeek:Int<br>department:String<br>intro:String<br>year:Int | 修改指定课程的信息 |
|  GET   | /course/get/info |                     cid:String(required)                     | status:String<br>cid:String<br>name:String<br>credit:Float<br>numLessonsEachWeek:Int<br>department:String<br>intro:String<br>year:Int | 获取指定课程的信息 |
|  POST  |  /course/query   | cid:String<br>name:String(optional)<br>department:String(optional) | status:String<br>cids:String[]<br>names:String[]<br>departments:String[]<br>genders:String[]<br>types:String[]<br>years:Int[] |    模糊查找课程    |



### 4. 院系管理

​	需要为系统/教务管理员提供增删开课院系、专业、专业班，遍历院系、专业、专业班和修改院系、专业、专业班信息的接口。所有的请求都需要在请求头中添加token。

|  方法  |            uri            |                        请求参数(json)                        |                        返回参数(json)                        |                     说明                     |
| :----: | :-----------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :------------------------------------------: |
|  PUT   |   /dept/department/add    |                    name:String(required)                     |                 status:String<br>name:String                 |                   添加院系                   |
|  POST  | /dept/department/add/user |   department:String(required)<br>uids:String[] (required)    |       status:String<br>department:String<br>uid:String       |    为院系批量添加用户，有专业班的设置无效    |
| DELETE |  /dept/department/delete  |                    name:String(required)                     |                 status:String<br>name:String                 |                   删除院系                   |
|  POST  |  /dept/department/modify  |      name:String(required)<br>newName:String(required)       |        status:String<br>name:String<br>newName:String        |              修改指定院系的信息              |
|  POST  | /dept/department/get/info |                    name:String(required)                     |       status:String<br> name:String<br>majors:String[]       |              获取指定院系的信息              |
|  POST  | /dept/department/get/list |                             None                             |               status:String<br>names:String[]                |                获取所有的院系                |
|  PUT   |      /dept/major/add      |    name:String(required)<br> department:String(required)     |      status:String<br>name:String<br>department:String       |                   添加专业                   |
| DELETE |    /dept/major/delete     |                    name:String(required)                     |                 status:String<br>name:String                 |                   删除专业                   |
|  POST  |    /dept/major/modify     | name:String(required)<br>newName:String(optional)<br>department:String(optional) | status:String<br>name:String<br>newName:String<br>department:String |              修改指定专业的信息              |
|  POST  |   /dept/major/get/info    |                    name:String(required)                     | status:String<br>name:String<br>department:String<br>classes:String[] |              获取指定专业的信息              |
|  PUT   |      /dept/class/add      |       name:String(required)<br>major:String(required)        |   status:String<br>name:String<br>major:String<br>year:Int   |                  添加专业班                  |
|  POST  |   /dept/class/add/user    |   majorClass:String(required)<br>uids:String[] (required)    |       status:String<br>majorClass:String<br>uid:String       | 为专业班批量添加用户，同时更新用户的院系信息 |
| DELETE |    /dept/class/delete     |                    name:String(required)                     |                 status:String<br>name:String                 |                  删除专业班                  |
|  POST  |    /dept/class/modify     | name:String(required)<br>newName:String(optional)<br>major:String(optional)<br>year:Int(optional) |   status:String<br>name:String<br>major:String<br>year:Int   |             修改指定专业班的信息             |
|  POST  |   /dept/class/get/info    |                    name:String(required)                     | status:String<br>name:String<br>major:String<br>year:Int<br>unames:String[]<br>uids:String[] |             获取指定专业班的信息             |



### 5. 会话管理

​	需要为所有的用户提供登录和注销接口。注销时需要在请求头中添加token。

|  方法  |       uri       |       请求参数(json)       |                       返回参数(json)                       |   说明   |
| :----: | :-------------: | :------------------------: | :--------------------------------------------------------: | :------: |
|  Post  | /session/login  | uid:String password:String | status:String<br>uid:String<br>type:String<br>token:String | 用户登录 |
| Delete | /session/logout |            None            |                status:String<br>uid:String                 | 用户注销 |

