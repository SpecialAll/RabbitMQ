package com.zxh.rabbitmq.spring;

public class MyConsumer {

    //执行业务方法
    public void listen(String foo){
        System.out.println("消费者----"+foo);
    }
}
