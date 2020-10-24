package com.baizhi.activemq;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//p2p发送消息 生产者 点对点
@Component
public class Producer {
    @Resource
    JmsTemplate jmsTemplate;

    //发送字符串消息
    public void sendMessage(String msg) {
        //声明消息名字
        /*ActiveMQQueue activeMQQueue = new ActiveMQQueue("springqueue");
        jmsTemplate.convertAndSend(activeMQQueue,msg);*/

        //这是发布订阅 的 发布  配置文件里设置true
        ActiveMQTopic activeMQTopic = new ActiveMQTopic("springTopic");
        jmsTemplate.convertAndSend(activeMQTopic, msg);
    }

    //发送对象消息
    /*public void sendMessage(User user){
        //声明消息名字
        ActiveMQQueue activeMQQueue = new ActiveMQQueue("springqueue");
        jmsTemplate.convertAndSend(activeMQQueue,user);
    }*/
}
