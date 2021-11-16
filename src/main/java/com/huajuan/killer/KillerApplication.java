package com.huajuan.killer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("")
@SpringBootApplication
public class KillerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KillerApplication.class, args);
    }

}
