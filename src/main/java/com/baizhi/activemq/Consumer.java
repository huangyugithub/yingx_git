package com.baizhi.activemq;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;

//消费者
@Component
public class Consumer {
    @Resource
    JmsTemplate jmsTemplate;

    //对springqueue队列进行监听，如果操作当前队列，就获取数据
    @JmsListener(destination = "springTopic")
    public void consumerReceive(TextMessage message) {
        System.out.println("heeee");
        try {
            String text = message.getText();
            System.out.println("消费者" + text);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
   /* @JmsListener(destination = "springqueue")
    public void consumerReceiveUser(ObjectMessage message){
        System.out.println("heeee");
        try {
            User text = (User) message.getObject();
            System.out.println("消费者"+text);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }*/
}
