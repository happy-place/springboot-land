# kafka
```shell script

docker pull wurstmeister/zookeeper
docker pull wurstmeister/kafka

docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper

#10.60.129.173 为宿主机IP
docker run -d --name kafka \
--publish 9092:9092 \
--link zookeeper \
--env KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
--env KAFKA_ADVERTISED_HOST_NAME=10.60.129.173 \
--env KAFKA_ADVERTISED_PORT=9092 \
wurstmeister/kafka:latest



#进入容器
docker exec -it kafka /bin/bash
cd /opt/kafka_2.12-2.3.0/bin

#创建主题
sh kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 2 --topic test

#删除主题【需要在server.properties中设置delete.topic.enable=true】
sh kafka-topics.sh --delete --zookeeper zookeeper:2181 --topic test

#查看主题：
sh kafka-topics.sh --list --zookeeper zookeeper:2181
sh kafka-topics.sh --describe --zookeeper zookeeper:2181 --topic test

#生产消息：
sh kafka-console-producer.sh --broker-list localhost:9092 --topic test

#消费消息：
# 从最早开始
sh kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic test
# 从最新开始
sh kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test
```