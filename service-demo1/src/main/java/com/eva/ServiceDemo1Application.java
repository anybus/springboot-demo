package com.eva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServiceDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDemo1Application.class, args);
    }
}
