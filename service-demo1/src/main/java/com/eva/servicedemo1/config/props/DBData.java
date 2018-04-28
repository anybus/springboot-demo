package com.eva.servicedemo1.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "master1.datasource")
@Data
public class DBData {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
