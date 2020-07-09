# AOP
```text
1. @Aspect @Component : 声明切面并将切面注册到IOC容器；
2. 启动类实现 CommandLineRunner 接口，在启动Application时显示调用run执行需要的初始化操作；
3. @AfterReturning("execution(* com..*Service.*(..))") 后置返回通知，在目标函数执行return之后，执行切面逻辑  
```