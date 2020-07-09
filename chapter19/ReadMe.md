# Mongodb
```text
test 使用的是内嵌mongo
main 使用的是外置mongo
```
## 下载、运行、登录
```shell
docker pull mongo:latest
docker run -itd --name mongo -p 27017:27017 mongo --auth
docker exec -it mongo mongo admin
```
## 创建授权admin用户
```
# 创建admin用户
db.createUser({ user:'admin',pwd:'123456',roles:[ { role:'userAdminAnyDatabase', db: 'admin'}]});

# admin用户授权
db.auth('admin', '123456')
```
## 创建授权普通用户
```
# 创建普通用户
db.createUser({user: "test",pwd: "test",roles: [ "readWrite"]});

# 对普通用户授权
db.auth('test','test');
```
