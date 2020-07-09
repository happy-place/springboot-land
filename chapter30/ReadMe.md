# jetty-ssl
```text
我们知道，HTTP 协议都是明文传输内容，在早期只展示静态内容时没有问题。伴随着互联网的快速发展，人们对于网络传输安全性的要求也越来越高，HTTPS 协议因此出现。如上图所示，在 HTTPS 加密中真正起作用的其实是 SSL/TLS 协议。SSL/TLS 协议作用在 HTTP 协议之下，对于上层应用来说，原来的发送接收数据流程不变，这就很好地兼容了老的 HTTP 协议，这也是软件开发中分层实现的体现。
SSL/TLS 握手是为了安全地协商出一份对称加密的秘钥，这个过程很有意思，下面我们一起来了解一下。
```
```shell script
# 创建 sample.jks 证书
keytool -genkeypair -alias foo -keyalg RSA -keypass jettypass -keystore jetty -storepass jettypass -keystore sample.jks
```