package com.eva.servicedemo1.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.eva.dbutil.config.DruidDataSourceConfig;
import com.eva.dbutil.datasources.DataSourceContextHolder;
import com.eva.dbutil.datasources.DynamicDataSource;
import com.eva.dbutil.props.DruidDataSourceProperties;
import com.eva.servicedemo1.config.props.DBData;
import com.eva.servicedemo1.config.props.DBData2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({DruidDataSourceProperties.class,DBData.class,DBData2.class})
public class CustomDataSourceConfig extends DruidDataSourceConfig {
    @Autowired
    private DruidDataSourceProperties p;
    @Autowired
    private DBData db1;

    @Autowired
    private DBData2 db2;


    private DataSource createDataSource(Object db){
        DBData dbData = null;
        DruidDataSource druidDataSource = new DruidDataSource();
        if(db instanceof DBData){
            druidDataSource.setUrl(((DBData)db).getUrl());
            druidDataSource.setUsername(((DBData)db).getUsername());
            druidDataSource.setPassword(((DBData)db).getPassword());
            druidDataSource.setDriverClassName(((DBData)db).getDriverClassName());
        }else {
            druidDataSource.setUrl(((DBData2)db).getUrl());
            druidDataSource.setUsername(((DBData2)db).getUsername());
            druidDataSource.setPassword(((DBData2)db).getPassword());
            druidDataSource.setDriverClassName(((DBData2)db).getDriverClassName());
        }
        // configuration
        druidDataSource.setInitialSize(p.getInitialSize());
        druidDataSource.setMinIdle(p.getMinIdle());
        druidDataSource.setMaxActive(p.getMaxActive());
        druidDataSource.setMaxWait(p.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(p.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(p.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(p.getValidationQuery());
        druidDataSource.setTestWhileIdle(p.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(p.isTestOnBorrow());
        druidDataSource.setTestOnReturn(p.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(p.isPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(p.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            List<Filter> proxyFilters = new ArrayList<Filter>();
            WallFilter statFilter = new WallFilter();
            WallConfig config = new WallConfig();
            config.setMultiStatementAllow(true); // 批量操作
            statFilter.setConfig(config);
            proxyFilters.add(statFilter);
            druidDataSource.setProxyFilters(proxyFilters);
            druidDataSource.setFilters(p.getFilters());
        } catch (SQLException e) {
//            logger.error("druid configuration initialization filter", e);
        }
        druidDataSource.setConnectionProperties(p.getConnectionProperties());
        return druidDataSource;
    }

    public DataSource primaryDataSource() throws SQLException {
        return createDataSource(db1);
    }


    public DataSource dataSource2() throws SQLException {
        return createDataSource(db2);
    }
    /**
     * 使用多数据源(包括一个数据源)要注解掉DataSource的@Bean
     * 数据源增加或减少要和DataSourceContextHolder类一起改动
     *
     * @return
     * @throws SQLException
     */
    @Bean
    @Qualifier("dataSource")
    public DynamicDataSource dynamicDataSource() throws SQLException {
        // 默认数据源
        DataSource primaryDataSource = primaryDataSource();
        // 第二个数据源
        DataSource dataSource2 = dataSource2();
        DynamicDataSource dataSource = new DynamicDataSource();
        // 这里设置默认数据源
        dataSource.setDefaultTargetDataSource(primaryDataSource);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        // 多数据源(如果使用一个的话就配置一个)
        // 要添加的话在写一个获取数据源的方法
        targetDataSources.put(DataSourceContextHolder.PRIMARY_DATA_SOURCE, primaryDataSource);
        targetDataSources.put(DataSourceContextHolder.DATA_SOURCE_B, dataSource2);
        dataSource.setTargetDataSources(targetDataSources);
        return dataSource;
    }

}
