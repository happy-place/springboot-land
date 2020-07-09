# Solr
```text
    Solr（读作“solar”）是Apache Lucene项目的开源企业搜索平台。 其主要功能包括全文检索、命中標示、分面搜索、动态聚类、数据库集成，
以及富文本（如Word、PDF）的處理。 Solr是高度可扩展的，并提供了分布式搜索和索引复制。 Solr是最流行的企业级搜索引擎，
Solr 4还增加了NoSQL支持

dev: 内嵌solr
prod: solr on docker
```
## 
```shell script
# 拉取镜像
docker pull solr:7.5.0
# 运行solr，http://localhost:8983/solr/#
docker run --name my-solr -d -p 8983:8983 -t solr:7.5.0
# 创建core
docker exec -it --user=solr my-solr bin/solr create_core -c collection1
```
