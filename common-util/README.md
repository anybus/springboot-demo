### common-util 模块

封装一些常用的工具类
pom配置:
```$xslt
<parent>
        <groupId>com.eva</groupId>
        <artifactId>eva-boots</artifactId>
        <version>1.0.1</version>
        <relativePath>../pom.xml</relativePath>
</parent>   /*依赖主模块*/

注：由于该模块不需要打springboot包（可独立执行的包），所以 默认的 build 删除掉，maven会按普通的jar包方式进行打包
这个很关键!
```

本模块测试了属性文件的跨模块读取
在common_util模块存放了commonutil.properties
在web模块service-demo1的DemoRestController中加载并读取属性值
注解配置如下：
@PropertySource(value = "classpath:commonutil.properties")   类注解
@Value("${testName}")
private String testName;           属性注解

从jar包的情况看：
service-demo1-0.0.1-SNAPSHOT.jar
- BOOT-INF
  - classes   ***service-demo1 web模块的代码所在***
    - com.eva
    - mapper
    - ...yml
  - lib
    - common-util-1.0.1.jar
      - com.eva     common_util package
      - commonutil.properties  存放在 resources中的资源文件

可以看出，
1. springboot的包结构和普通jar包结构不太一样；
2. springboot可以读取其他jar包的根目录下的配置文件。