# dev-tools
```text
    平日里开发项目中，修改了Java代码或者配置文件的时候，必须手动重启项目才能生效。
所谓的热部署就是在你修改了后端代码后不需要手动重启，工具会帮你快速的自动重启是修改生效。
    其深层原理是使用了两个ClassLoader，一个Classloader加载那些不会改变的类（第三方Jar包），
另一个ClassLoader加载会更改的类，称为restart ClassLoader，这样在有代码更改的时候，
原来的restart ClassLoader 被丢弃，重新创建一个restart ClassLoader，由于需要加载的类相比较少，
所以实现了较快的重启时间
```

## idea + dev-tools
* maven 依赖
```xml
<!-- 热部署模块 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional> <!-- 这个网上说需要设置为 true 热部署才有效,实测没有也可以的 -->
</dependency>
```
* idea 动态编译开关
```text
运行项目变更时，自动编译
Preferences > Build, Execution, Deployment > Compiler (勾选Build project automatically)
```
* 允许运行时触发自动编译
```text
Shift+Ctrl+Alt+/(Mac是Cmd+Shift+A) 或 双shift，搜索Registery，勾选 compiler.automake.allow.when.app.running
```

## dev-tools 默认配置
```properties
# Whether to enable a livereload.com-compatible server.
spring.devtools.livereload.enabled=true

# Server port.
spring.devtools.livereload.port=35729

# Additional patterns that should be excluded from triggering a full restart. 监控修改额外剔除目录
spring.devtools.restart.additional-exclude= 

# Additional paths to watch for changes. 监控修改额外增加目录
spring.devtools.restart.additional-paths= 

# Whether to enable automatic restart. 是否允许重启
spring.devtools.restart.enabled=true

# Patterns that should be excluded from triggering a full restart. 重启策略忽略面目录变更
spring.devtools.restart.exclude=META-INF/maven/**,META-INF/resources/**,resources/**,static/**,public/**,templates/**,**/*Test.class,**/*Tests.class,git.properties,META-INF/build-info.properties

# Whether to log the condition evaluation delta upon restart. 重启过程是否记录日志
spring.devtools.restart.log-condition-evaluation-delta=true

# Amount of time to wait between polling for classpath changes. 扫描变更间隔
spring.devtools.restart.poll-interval=1s

# Amount of quiet time required without any classpath changes before a restart is triggered. 类路径变更触发重启前冷静期(在此这期间改回去，无影响)
spring.devtools.restart.quiet-period=400ms

# Name of a specific file that, when changed, triggers the restart check. If not specified, any classpath file change triggers the restart.指定触发重启文件，默认是全部文件修改都将触发重启
spring.devtools.restart.trigger-file=
```

## 实操
* 启动项目 
```java
@Controller
public class MyController {

	@PostConstruct
	public void slowRestart() throws InterruptedException {
		// 控制器创建后休眠5s，再启动
		Thread.sleep(5000);
	}

	@GetMapping("/")
	public ModelAndView get(HttpSession session) {
		Object sessionVar = session.getAttribute("var");
		if (sessionVar == null) {
			sessionVar = new Date();
			session.setAttribute("var", sessionVar);
		}
		ModelMap model = new ModelMap("message", Message.MESSAGE)
				.addAttribute("sessionVar", sessionVar);
		return new ModelAndView("hello", model);
	}

}
``` 
* 访问 http://localhost:8080/
```java
@Controller
public class MyController {

	@PostConstruct
	public void slowRestart() throws InterruptedException {
		// 控制器创建后休眠5s，再启动
		Thread.sleep(5000);
	}

	@GetMapping("/")
	public ModelAndView get(HttpSession session) {
		Object sessionVar = session.getAttribute("var");
		if (sessionVar == null) {
			sessionVar = new Date();
			session.setAttribute("var", sessionVar);
		}
		ModelMap model = new ModelMap("message", Message.MESSAGE)
				.addAttribute("sessionVar", sessionVar);
        // 修改响应，从日志上看已经动态部署 
		return new ModelAndView("hello world", model); 
	}

}
```
