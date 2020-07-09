# neo4j
```text
dev 使用的是内嵌neo4j
prod 使用的是外置neo4j
```

```shell script
docker pull neo4j:latest
docker run -d --name neo4j -p 7474:7474 -p 7687:7687 neo4j

登录 http://localhost:7474/browser/ 强制充值密码 neo4j/tomcat
```