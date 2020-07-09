# jwt
```text
不需要向 Google 索要 access token，而是携带 JWT 作为 HTTP header 里的 bearer token 直接访问 API 也是可以的。
我认为这才是 JWT 的最大魅力。

JWT 顾名思义，它是 JSON 结构的 token，由三部分组成：1) header 2) payload 3) signature

header 用于描述元信息，例如产生 signature 的算法：
{
    "typ": "JWT",
    "alg": "HS256"
}
其中alg关键字就指定了使用哪一种哈希算法来创建 signature

payload 用于携带你希望向服务端传递的信息。你既可以往里添加官方字段（这里的“字段” (field) 也可以被称作“声明” claims），例如iss(Issuer),
 sub(Subject), exp(Expiration time)，也可以塞入自定义的字段，比如 userId:
{
    "userId": "b08f86af-35da-48f2-8fab-cef3904660bd"
}

signature

创建签名要分以下几个步骤：

你需要从接口服务端拿到密钥，假设为secret
将header进行 base64 编码，假设结果为headerStr
将payload进行 base64 编码，假设结果为payloadStr
将headerStr和payloadStr用.字符串拼装起来成为字符data
以data和secret作为参数，使用哈希算法计算出签名

回想一下，当你拿到 JWT 时候，你完全可以在没有 secret 的情况下解码出 header 和 payload，因为 header 和 payload 
只是经过了 base64 编码（encode）而已，编码的目的在于利于数据结构的传输。虽然创建 signature 的过程近似于加密 (encrypt)，
但本质其实是一种签名 (sign) 的行为，用于保证数据的完整性，实际上也并且并没有加密任何数据


```