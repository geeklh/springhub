package com.geekli;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.geekli.dao")
public class MyspringdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyspringdemoApplication.class, args);
    }

}
