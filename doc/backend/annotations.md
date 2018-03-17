## Annotations Usage

`@Authorization`

注解在 Controller 中的方法上，表示此方法在未登录的情况下访问会直接返回 401 错误。

注意，不检查用户身份，只要登录即可。

`@CurrentUser`

注解在 Controller 方法的参数上，参数类型需为 UserEntity，这个参数会被注入为当前登录用户的数据。

注意，不检查用户是否存在，可能导致异常。大部分情况下建议配合上一个注解使用。

举例如下：

```java
    @GetMapping("/auth")
    @Authorization
    public String auth(@CurrentUser UserEntity user) {
        return "Login as " + user.getUid();
    }
```

