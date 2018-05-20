## information APIs

### 1. 权限配置

​	当前设计中，权限通过后端的配置文件进行配置，不为前端提供接口。具体配置格式参考`backend/init_date.md`。

### 2. 用户管理

​	需要为系统/教务管理员提供对用户数据库进行增删查改的接口，同时需要为每个用户提供查看和修改基本信息，修改密码，上传图片资源的接口。所有的请求都需要在请求头中添加token。

|  方法  |        uri         |                        请求参数(json)                        |                        返回参数(json)                        |               说明               |
| :----: | :----------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :------------------------------: |
|  PUT   |     /user/add      | uid:String[] (required) password:String[] (required) name:String[] (required) gender:string[] (required) type:String (required) | status:String uid:String name:String gender:String type:String | 批量添加用户，失败后返回失败原因 |
| DELETE |    /user/delete    |                   uid:String[] (required)                    |                        status:String                         | 批量删除用户，失败后返回失败原因 |
|  POST  |  /user/modify/pwd  | id:Int(required) oldPassword:String(required) newPassword:String(required) |                        status:String                         |        更改当前用户的密码        |
|  POST  |  /user/reset/pwd   |                       id:Int(required)                       |                        status:String                         |        重置指定用户的密码        |
|  POST  |  /user/modify/own  | email:String(optional) telephone:String(optional) intro:String(optional) |  status:String email:String telephone:String  intro:String   |      修改当前用户的基本信息      |
|  POST  |  /user/modify/all  | uid:String(required) name:String(optional) gender:String(optimal)  major_class:String(optimal) type:String(optional) department:String(optional) email:String(optional) telephone:String(optional) intro:String(optional) | status:String name:String gender:String major_class:String type:String department:String email:String telephone:String intro:String |      修改指定用户的所有信息      |
|  GET   |   /user/get/info   |                     uid:String(required)                     | status:String  uid:String name:String gender:String major_class:String type:String department:String email:String telephone:String intro:String |        查找指定用户的信息        |
|  POST  |    /user/query     | uid:String(optional) name:String(optional) deptName:String(optional) | status:String uids:String[] names:String[] department:String[] major_classes:String[] |           用户模糊查询           |
|  PUT   | /user/modify/photo |         uid:String(required) photo:String(required)          |                        status:String                         |           用户上传照片           |
|  GET   |  /user/get/photo   |                     uid:String(required)                     |                  status:String photo:String                  |           获取用户照片           |



### 3. 课程管理

​	需要为系统/教务管理员提供增删课程，遍历课程和修改课程信息的接口。所有的请求都需要在请求头中添加token。

|  方法  |       uri        |                        请求参数(json)                        |                        返回参数(json)                        |        说明        |
| :----: | :--------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------: |
|  PUT   |   /course/add    | cid:String(required) name:String(requierd) credit:Float(optional) weeklyNum:Int(optional) semester:String(optional)  department:String(optinal) | status:String   cid:String    name:String credit:Float weeklyNum:Int semester:String  department:String |      添加课程      |
| DELETE |  /course/delete  |                     cid:String(required)                     |          status:String   cid:String    name:String           |      删除课程      |
|  POST  |  /course/modify  | cid:String(required) name:String(optional) credit:Float(optional) weeklyNum:Int(optional) semester:String(optional)  department:String(optinal) intro:String(optional) | status:String   cid:String    name:String credit:Float weeklyNum:Int semester:String  department:String  intro:String | 修改指定课程的信息 |
|  GET   | /course/get/info |                     cid:String(required)                     | status:String   cid:String    name:String credit:Float weeklyNum:Int semester:String  department:String  intro:String | 获取指定课程的信息 |
|  POST  |  /course/query   |      name:String(optional) department:String(optional)       | status:String   cids:String[] names:String[] departments:String[] |    模糊查找课程    |



### 4. 院系管理

​	需要为系统/教务管理员提供增删开课院系、专业、专业班，遍历院系、专业、专业班和修改院系、专业、专业班信息的接口。所有的请求都需要在请求头中添加token。

|  方法  |         uri          |                        请求参数(json)                        |                        返回参数(json)                        |         说明         |
| :----: | :------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :------------------: |
|  PUT   |    /dept/add/dept    |                    name:String(required)                     |                status:String     name:String                 |       添加院系       |
| DELETE |  /dept/delete/dept   |                    name:String(required)                     |                status:String     name:String                 |       删除院系       |
|  POST  |  /dept/modify/dept   |                    name:String(required)                     |                 status:String    name:String                 |  修改指定院系的信息  |
|  GET   | /dept/get/dept/info  |                    name:String(required)                     |         status:String    name:String majors:String[]         |  获取指定院系的信息  |
|  GET   | /dept/get/dept/list  |                             None                             |               status:String     name:String[]                |    获取所有的院系    |
|  PUT   |   /dept/add/major    |      name:String(required)  department:String(required)      |        status:String    name:String department:String        |       添加专业       |
| DELETE |  /dept/delete/major  |                    name:String(required)                     |                 status:String    name:String                 |       删除专业       |
|  POST  |  /dept/modify/major  | name:String(required) newName:String(optional) department:String(optional) |        status:String    name:String department:String        |  修改指定专业的信息  |
|  GET   | /dept/get/major/info |                    name:String(required)                     | status:String name:String department:String classes:String[] |  获取指定专业的信息  |
|  PUT   |   /dept/add/class    |         name:String(required) major:String(required)         |  status:String    name:String major:String        year:Int   |      添加专业班      |
| DELETE |  /dept/delete/class  |                    name:String(required)                     |                 status:String    name:String                 |      删除专业班      |
|  POST  |  /dept/modify/class  | name:String(required) newName:String(optional) major:String(optional) year:Int(optional) |  status:String    name:String major:String        year:Int   | 修改指定专业班的信息 |
|  GET   | /dept/get/class/info |                    name:String(required)                     | status:String    name:String major:String        year:Int  unames:String[] uids:String[] | 获取指定专业班的信息 |



### 5. 会话管理

​	需要为所有的用户提供登录和注销接口。注销时需要在请求头中添加token。

|  方法  |       uri       |       请求参数(json)       |                  返回参数(json)                   |   说明   |
| :----: | :-------------: | :------------------------: | :-----------------------------------------------: | :------: |
|  Post  | /session/login  | uid:String password:String | status:String uid:String type:String token:String | 用户登录 |
| Delete | /session/logout |            None            |             status:String uid:String              | 用户注销 |

