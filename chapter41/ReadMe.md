# oauth2 authenticated by github
```text
多平台授权登录绑定
```
## 注册 github 账户

## 注册 github 授权回调
```text
生成github 授权信息 client-id/client-secret
http://localhost:8080/login/oauth2/code/github

回调地址填：http://localhost:8080/login/oauth2/code/github
```

## 尝试以github作为secret-server对服务进行访问
```text
启动client -DAPP-CLIENT-ID=xxxx -DAPP-CLIENT-SECRET=yyyyy
http://localhost:8080 跳转github登录页面，email 登录成功后，再次访问 http://localhost:8080 即可看到返回值
```