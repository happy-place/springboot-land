# spring-boot-starter-amqp
## 简介
```text
1. docker-compose.yml 部署  rabbmitmq 实例提供MQ支持；
2. 启动Application，通过扫描包，启动Sender中定时任务，周期性向 foo 队列发送消息；
3. @RabbitListener(queues = "foo") 定义了 @RabbitHandler 需要监听的队列；
4. @RabbitHandler 监听并接受 foo 产生的新消息，打印输出
``` 
