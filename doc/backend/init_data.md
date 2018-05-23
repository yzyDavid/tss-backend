## Data Initialization

​	以下所有内容需要将**ddl-auto(resources/application.properties)设置create**后才会生效。在初始数据生成结束后，可以将生成策略改回update。

```
spring.jpa.hibernate.ddl-auto=create
```

### 系统管理员账户

​	程序初始化时会自动生成一个系统管理员账户，账户的账号密码与数据库的账号密码相同。

```
spring.datasource.username=xxxxxxxx
spring.datasource.password=xxxxxxxx
```

### 插入初始数据

​	在resources目录下有一个**import.sql**，可以直接在该文件中写SQL语句来插入初始数据。比如：

```
INSERT INTO department VALUES(0, 'Computer Science');
INSERT INTO course VALUES(0, 2, 'an important introductory course', 'Data Structure', 'S', 5, 0);
```

​	注意，由于数据库中保存的是的用户密码的Hash值，添加用户仍需通过Postman发送AddUser请求(UserController)。

### 用户权限配置

​	原UserEntity中的type字段已弃用，目前采取RBAC模型进行权限控制。各类用户的角色权限配置在**resources/role.json**中完成。下面举例说明：

```
"department_administrator" : {
    "prefix" : "dept",
    "paths" : ["add", "delete", "modify"],
    "belong2" : [0, 1]
  }
```

​	在RBAC模型中，权限(authority)具体体现为资源的uri；而角色(role)则是一个抽象的权限集合体，它与用户类型(user type)是不同的。

​	"department_administrator"为角色名，可以自行决定。prefix与Controller类RequestMapping注解中的path字段相同，paths与方法的xxxMapping注解的path字段相同。belong2代表该角色会被分配给哪些类型的用户，此处出现的是用户类型的index。完整的用户类型包括以下四种：

```
public static final String[] TYPES = {"System Administrator", "Teaching Administrator", "Teacher", "Student"};
```

​	比如，本例中，名为"department_administrator"的角色拥有通过调用uri为"dept/add", "dept/delete", "dept/modify"的方法的权限。而这一角色同时被分配给了系统管理员(System Administrator)和教务管理员(Teaching Administrator)这两个类型的用户。

​	后端编写者可以根据需求为每一个Controller构建不同的角色，并为它们分配权限。最后，再通过belong2字段，为不同类型的用户分配角色。权限配置结束后，只要在需要进行访问控制的Controller方法前添加@Authorization注解即可。

​	权限控制只在方法入口处进行拦截，提供用户身份类型和方法访问控制的保证，更细粒度的DAO层资源访问控制目前还在构思中。