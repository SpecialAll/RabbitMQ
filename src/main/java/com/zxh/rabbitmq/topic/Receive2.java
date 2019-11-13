package com.zxh.rabbitmq.topic;

import com.rabbitmq.client.*;
import com.zxh.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive2 {
    private static final String EXCHANGE_NAME = "test_exchange_topic";

    private static  final String QUEUE_NAME = "test_queue_topic_2";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);


        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.add");

        channel.basicQos(1);

        //定义一个消费者
        Consumer consumer = new DefaultConsumer(channel){
            //触发消息方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[2] work msg : "+ msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[2] -------done-------");
                }
            }
        };

        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);

    }
}
