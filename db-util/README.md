# db-util 模块

##模块功能: 
- druid连接池配置
- 多数据源配置
- mybatis配置
- jpa/hibernate配置（暂未实现）

##特点：
- 功能分离。
网上的代码样例牵扯到数据源、连接池、mybatis等配置时，通常都是糅合在一起，导致在定制特定需求的时候，往往无从下手。
- 其他。都是网上copy实现的，如数据源注解切换、druid连接池配置等

##功能分离介绍：
- ###独立的druid连接池配置
    - DruidDataSourceConfig
        -- ServletRegistrationBean
        -- FilterRegistrationBean
        - 网上配置很多，略
    - application-db.yml
        - 将数据库链接配置剔除出去，仅保留druid的配置
        - 数据库连接的配置移步到service-demo1中，service-demo1是一个项目的入口模块，这里集中配置该项目的
        各种方案，而db-util模块则很好地独立了出来
    - DruidDataSourceProperties
        - 注解读取yml中的druid配置
        - 使用  @PropertySource({"classpath:application-db.yml"})  和 @ConfigurationProperties(prefix = "druid")
        来加载响应的配置项
    - DruidDataSourceBuilder
        - 创建druid数据源的工厂类
        - 传入数据库的url/username/password/driverClassName来构建数据源对象
    在service-demo1中配置数据源，在DataSource中继承DruidDataSourceConfig 并配置 DataSource 和 PlatformTransactionManager
    ，这样就组成了完整的数据源配置，并且实现了功能的分离
- ###数据源切换的注解配置
    - 核心类：DynamicDataSource  extends AbstractRoutingDataSource
    继承了spring jdbc的抽象路由数据源这个类，接下来的就比较简单了
    1. 先定义一个数据源注解类 ----------  DS
    2. 实现注解类的切面配置，在before中获取注解配置的数据源，after清楚注解配置源 ------------ DynamicDataSourceAspect
    3. 使用一个容器装注解数据源 -------------- DataSourceContextHolder
        这里使用的是ThreadLocal<String> contextHolder  ,后续可以了解一下~
    触发数据源切换的逻辑都是由AbstractRoutingDataSource来实现的，后续可以了解。这里我们只是实现了一个注解传值的小功能！
    
    - DynamicDataSource在service-demo1数据源配置中，担当了注册DataSource Bean的功能，详情可参看DataSourceConfig代码

- ###mybatis通用mapper配置
    尽管通用mapper可能不够完善，好在它实现了基本功能的同时，并不影响我们使用原生mtbatis功能，国货，还是支持一下！
    （这个国货没啥文档，好像作者主要想卖书）
    yml配置，这个配置没有放在db-util中，主要是包和mapper路径配置，属于项目本身的自定义配置.
    ```$xslt
    application-dao.yml 配置
    mybatis:
      type-aliases-package: com.eva.model
      mapper-locations: classpath:mapper/*.xml
    mapper:
        mappers:
            - com.eva.core.MyMapper
        not-empty: false
        identity: MYSQL
    
    pagehelper:
        helperDialect: mysql
        reasonable: true
        supportMethodsArguments: true
        params: count=countSql
    
    logging:
      level:
        com.eva.dao: DEBUG
    ```
    做完如上配置，我们只需要自定义MyMapper接口即可，这个mapper接口继承了通用mapper的好几个接口，就不一一介绍了！
    我们的各个业务类的mapper接口只需要继承MyMapper即可，就能方便实现单表的各种操作，批量更新和删除除外！（作者没实现）
    批量插入还是可以的，效率很高
    
注：勿忘程序入口的两个注解，
    @MapperScan(basePackages = "com.eva.dao")
    @EnableTransactionManagement
    一个是通用mapper需要的，一个是开启事务需要的！

    