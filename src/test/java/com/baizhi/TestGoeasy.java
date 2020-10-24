package com.baizhi;

import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGoeasy {

    @Test
    public void test1() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-4cfb0586a26049be88a1a45811d15004");
        goEasy.publish("yingx_channel", "Hello, GoEasy!");
    }
}
