To-do List application using Spring HATEOAS
=======================

A simple To-do List application written using [Spring HATEOAS](http://projects.spring.io/spring-hateoas/).

This simple application includes following components:

* Model and persistence using Spring Data JPA
* Resource and HAL
* Access control using Spring AOP

```text
REST 架构
    REST 是 Representational state transfer 的缩写，翻译过来的意思是表达性状态转换。REST 是一种架构风格，它包含了一个分布式超文本系统中对于组件、
连接器和数据的约束。REST 是作为互联网自身架构的抽象而出现的，其关键在于所定义的架构上的各种约束。只有满足这些约束，才能称之为符合 REST 架构风格。

REST 的约束包括：
1.客户端-服务器结构。通过一个统一的接口来分开客户端和服务器，使得两者可以独立开发和演化。客户端的实现可以简化，而服务器可以更容易的满足可伸缩性的要求。
2.无状态。在不同的客户端请求之间，服务器并不保存客户端相关的上下文状态信息。任何客户端发出的每个请求都包含了服务器处理该请求所需的全部信息。
3.可缓存。客户端可以缓存服务器返回的响应结果。服务器可以定义响应结果的缓存设置。
4.分层的系统。在分层的系统中，可能有中间服务器来处理安全策略和缓存等相关问题，以提高系统的可伸缩性。客户端并不需要了解中间的这些层次的细节。
5.按需代码（可选）。服务器可以通过传输可执行代码的方式来扩展或自定义客户端的行为。这是一个可选的约束。
6.统一接口。该约束是 REST 服务的基础，是客户端和服务器之间的桥梁。该约束又包含下面 4 个子约束。
7.资源标识符。每个资源都有各自的标识符。客户端在请求时需要指定该标识符。在 REST 服务中，该标识符通常是 URI。客户端所获取的是资源的表达（representation），通常使用 XML 或 JSON 格式。
8.通过资源的表达来操纵资源。客户端根据所得到的资源的表达中包含的信息来了解如何操纵资源，比如对资源进行修改或删除。
9.自描述的消息。每条消息都包含足够的信息来描述如何处理该消息。
10.超媒体作为应用状态的引擎（HATEOAS）。客户端通过服务器提供的超媒体内容中动态提供的动作来进行状态转换。这也是本文所要介绍的内容。

在了解 REST 的这些约束之后，就可以对“表达性状态转换”的含义有更加清晰的了解。“表达性”的含义是指对于资源的操纵都是通过服务器提供的资源的表达来进行的。
客户端在根据资源的标识符获取到资源的表达之后，从资源的表达中可以发现其可以使用的动作。使用这些动作会发出新的请求，从而触发状态转换。

HATEOAS 约束
    HATEOAS（Hypermedia as the engine of application state）是 REST 架构风格中最复杂的约束，也是构建成熟 REST 服务的核心。
它的重要性在于打破了客户端和服务器之间严格的契约，使得客户端可以更加智能和自适应，而 REST 服务本身的演化和更新也变得更加容易。

在介绍 HATEOAS 之前，先介绍一下 Richardson 提出的 REST 成熟度模型。该模型把 REST 服务按照成熟度划分成 4 个层次：
第一个层次（Level 0）的 Web 服务只是使用 HTTP 作为传输方式，实际上只是远程方法调用（RPC）的一种具体形式。SOAP 和 XML-RPC 都属于此类。
第二个层次（Level 1）的 Web 服务引入了资源的概念。每个资源有对应的标识符和表达。
第三个层次（Level 2）的 Web 服务使用不同的 HTTP 方法来进行不同的操作，并且使用 HTTP 状态码来表示不同的结果。如 HTTP GET 方法来获取资源，HTTP DELETE 方法来删除资源。
第四个层次（Level 3）的 Web 服务使用 HATEOAS。在资源的表达中包含了链接信息。客户端可以根据链接来发现可以执行的动作。
从上述 REST 成熟度模型中可以看到，使用 HATEOAS 的 REST 服务是成熟度最高的，也是推荐的做法。对于不使用 HATEOAS 的 REST 服务，
客户端和服务器的实现之间是紧密耦合的。客户端需要根据服务器提供的相关文档来了解所暴露的资源和对应的操作。当服务器发生了变化时，
如修改了资源的 URI，客户端也需要进行相应的修改。而使用 HATEOAS 的 REST 服务中，客户端可以通过服务器提供的资源的表达来智能地发现可以执行的操作。
当服务器发生了变化时，客户端并不需要做出修改，因为资源的 URI 和其他信息都是动态发现的。
```