# LADP
```text
Spring LDAP 是一个用于操作 LDAP 的 Java 框架。它是基于 Spring 的 JdbcTemplate 模式。
这个框架能够帮助开发人员简化 looking up，closing contexts，looping through NamingEnumerations，encoding/decoding values与 filters 等操作。

LDAP（Light Directory Access Portocol），它是基于X.500标准的轻量级目录访问协议。

目录是一个为查询、浏览和搜索而优化的数据库，它成树状结构组织数据，类似文件目录一样。

目录数据库和关系数据库不同，它有优异的读性能，但写性能差，并且没有事务处理、回滚等复杂功能，不适于存储修改频繁的数据。所以目录天生是用来查询的，就好象它的名字一样。

LDAP目录服务是由目录数据库和一套访问协议组成的系统。

Spring已经为我们对Ldap做了很好的封装，有ldapTemplate可以用，但是这里我们要介绍的是jldap，非官方的ldap工具。

```