# springboot-demo

## spring boot 模块化方案
集成的功能有：1. 模块化   2. 多数据源配置   3. druid连接池   4. mybatis框架使用
### 主模块配置 （根pom）

```$xslt
<packaging>pom</packaging> /* 主模块不需要打包，这里属性为pom*/

<modules>
        <module>common-util</module>
        <module>service-demo1</module>
        <module>db-util</module>
</modules>            /* 子模块 */

<build>
</build>  /*构建工具也可以删除掉*/
```
注：打包工作只能在主模块下进行，如果子模块A依赖了子模块B，在子模块A下打包，则会包maven库中无法
找到子模块B的错误，原因是在A下打包，maven会认为B是库里的jar包，它无法利用模块间的依赖关系！

### common-util 模块
[跳转至](https://github.com/anybus/springboot-demo/tree/master/common-util)
### db-util 模块
[跳转至](https://github.com/anybus/springboot-demo/tree/master/db-util)
### city-bus 模块
[跳转至](https://github.com/anybus/springboot-demo/tree/master/city-bus)
### app1 模块
[跳转至](https://github.com/anybus/springboot-demo/tree/master/app1)







