package com.baizhi;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySQLTimeOutTest {

    @Test
    //@Scheduled(cron = "0/10 * * * * ?")
    public void contextLoads() {
        Thread thread = Thread.currentThread();
        String name = thread.getName();
        System.out.println("测试1： " + name + "  " + new SimpleDateFormat("HH:mm:ss").format(new Date()));

    }

}
