# db-util 模块

模块功能: 
- druid连接池配置
- 多数据源配置
- mybatis配置
- jpa/hibernate配置（暂未实现）

特点：
- 功能分离。
网上的代码样例牵扯到数据源、连接池、mybatis等配置时，通常都是糅合在一起，导致在定制特定需求的时候，往往无从下手。
- 其他。都是网上copy实现的，如数据源注解切换、druid连接池配置等

功能分离介绍：
- 独立的druid连接池配置
    - DruidDataSourceConfig
        -- ServletRegistrationBean
        -- FilterRegistrationBean
        - 网上配置很多，略
    - application-db.yml
        - 将数据库链接配置剔除出去，仅保留druid的配置
    - DruidDataSourceProperties
        - 
    - DruidDataSourceBuilder