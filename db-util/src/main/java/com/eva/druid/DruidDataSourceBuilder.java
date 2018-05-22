package com.eva.druid;


import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DruidDataSourceBuilder {
    private DruidDataSource druidDataSource;
    private DruidDataSourceProperties p;

    public DruidDataSourceBuilder(DruidDataSourceProperties p){
        this.p = p;
        this.druidDataSource = new DruidDataSource();

    }
    private void init(){
//        this.druidDataSource.setDbType(p.getType());
        this.druidDataSource.setInitialSize(p.getInitialSize());
        this.druidDataSource.setMinIdle(p.getMinIdle());
        this.druidDataSource.setMaxActive(p.getMaxActive());
        this.druidDataSource.setMaxWait(p.getMaxWait());
        this.druidDataSource.setTimeBetweenEvictionRunsMillis(p.getTimeBetweenEvictionRunsMillis());
        this.druidDataSource.setMinEvictableIdleTimeMillis(p.getMinEvictableIdleTimeMillis());
        this.druidDataSource.setValidationQuery(p.getValidationQuery());
        this.druidDataSource.setTestWhileIdle(p.isTestWhileIdle());
        this.druidDataSource.setTestOnBorrow(p.isTestOnBorrow());
        this.druidDataSource.setTestOnReturn(p.isTestOnReturn());
        this.druidDataSource.setPoolPreparedStatements(p.isPoolPreparedStatements());
        this.druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(p.getMaxPoolPreparedStatementPerConnectionSize());
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
    }

    public DruidDataSourceBuilder url(String url){
        this.druidDataSource.setUrl(url);
        return this;
    }

    public DruidDataSourceBuilder username(String username){
        this.druidDataSource.setUsername(username);
        return this;
    }
    public DruidDataSourceBuilder password(String password){
        this.druidDataSource.setPassword(password);
        return this;
    }
    public DruidDataSourceBuilder driverClassName(String driverClassName){
        this.druidDataSource.setDriverClassName(driverClassName);
        return this;
    }

    public DruidDataSource build(){
        this.init();
        return this.druidDataSource;
    }

}
