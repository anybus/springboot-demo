# app1 模块

##模块功能: 
- 项目的入口模块，程序入口Application
- 各种配置集中于此，如数据源配置、redis配置等等

##说明
- 开发者可以把model、dao、service、controller移步到几个新的子模块，各自实现，这样的好处是每个开发者或者每个业务功能
都独立存在，不相互依赖！
- 示例业务功能已移至city-bus中

##yml配置介绍
```$xslt
yml模块引用
spring:
  profiles:
    active: dev    开发模式配置

dev.yml中
spring:
  profiles:
    include: db,dao   引用db、dao配置
```


