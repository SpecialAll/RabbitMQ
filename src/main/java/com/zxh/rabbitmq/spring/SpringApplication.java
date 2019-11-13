package com.zxh.rabbitmq.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;


public class SpringApplication {
    public static void main(String[] args) throws InterruptedException {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:context.xml");

        //rabbitmq模板
        RabbitTemplate template = context.getBean(RabbitTemplate.class);

        //发送消息
        template.convertAndSend("hello world!");
        Thread.sleep(1000);
        context.destroy();

    }
}
