package com.baizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@tk.mybatis.spring.annotation.MapperScan("com.baizhi.dao")
@MapperScan("com.baizhi.dao")
@SpringBootApplication
//@EnableScheduling //定时任务
//war包继承SpringBootServletInitializer  覆盖configure方法
public class YingxHuangyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(YingxHuangyApplication.class, args);
    }

    @Override
    //当前不在是启动内部服务器的入口类
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(YingxHuangyApplication.class);
    }
}
