## session 系统使用说明

`/session/login`

`POST`

```json
{
    "uid": "3150103978",
    "password": "whosyourdaddy"
}
```

若用户名密码正确，会返回一个 `JSON` 包含 `token` 字段。

随后的请求当中在请求头中附带 `X-Auth-Token` 字段，内容为上述返回的 token，即表明是以此用户身份操作。