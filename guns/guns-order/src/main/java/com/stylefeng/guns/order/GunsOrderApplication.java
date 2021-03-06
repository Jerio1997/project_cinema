package com.stylefeng.guns.order;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
@MapperScan("com.stylefeng.guns.order.common.persistence.dao")
public class GunsOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GunsOrderApplication.class, args);
    }
}
