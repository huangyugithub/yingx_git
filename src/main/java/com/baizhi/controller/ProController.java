package com.baizhi.controller;

import com.baizhi.activemq.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

//activeMQ 消费者  点对点
@Controller
@RequestMapping("pro")
public class ProController {
    //注入生产者
    @Resource
    Producer producer;

    @RequestMapping("send")
    @ResponseBody
    public void send(String message) {
        System.out.println(message);
        producer.sendMessage(message);
    }

    /*@RequestMapping("sendUser")
    @ResponseBody
    public void sendUser(User user){
        System.out.println(user);
        producer.sendMessage(user);
    }*/
}
