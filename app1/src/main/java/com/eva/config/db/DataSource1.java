package com.eva.config.db;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource.db1")
@Data
public class DataSource1 {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
