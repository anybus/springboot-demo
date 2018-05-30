package com.eva.config;

import com.eva.config.db.DataSource1;
import com.eva.config.db.DataSource2;
import com.eva.datasource.DynamicDataSource;
import com.eva.druid.DruidDataSourceBuilder;
import com.eva.druid.DruidDataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置类
 * Created by pure on 2018-05-06.
 */
@Configuration
@EnableConfigurationProperties({DataSource1.class, DataSource2.class,DruidDataSourceProperties.class})
public class DataSourceConfig extends DruidDataSourceConfig{

    @Autowired
    private DruidDataSourceProperties p;

    @Autowired
    private DataSource1 ds1;
    @Autowired
    private DataSource2 ds2;

    //数据源1
//    @Bean(name = "datasource1")
    public DataSource dataSource1() {
        return new DruidDataSourceBuilder(p)
                .url(ds1.getUrl())
                .username(ds1.getUsername())
                .password(ds1.getPassword())
                .driverClassName(ds1.getDriverClassName())
                .build();
    }

    //数据源2
//    @Bean(name = "datasource2")
    public DataSource dataSource2() {
        return new DruidDataSourceBuilder(p)
                .url(ds2.getUrl())
                .username(ds2.getUsername())
                .password(ds2.getPassword())
                .driverClassName(ds2.getDriverClassName())
                .build();
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     *
     * @return
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource1());
        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap();
        dsMap.put("datasource1", dataSource1());
        dsMap.put("datasource2", dataSource2());

        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    /**
     * 配置@Transactional注解事物
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
