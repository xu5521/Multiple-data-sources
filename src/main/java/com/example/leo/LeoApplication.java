package com.example.leo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.example.leo.mapper")
//@SpringBootApplication
public class LeoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeoApplication.class, args);
    }

}
