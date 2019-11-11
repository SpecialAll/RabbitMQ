package com.zxh.rabbitmq.workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zxh.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    /**
     *
     *
     */
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();

        //获取channel
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        /**
         * 每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
         *
         * 限制发送给同一个消费者 的消息不超过一条消息
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);


        for(int i=0; i<50; i++){
            String msgString = "hello" + i;

            channel.basicPublish("", QUEUE_NAME, null, msgString.getBytes());

            System.out.println("work queue send "+ msgString);
            Thread.sleep(i*20);
        }

        channel.close();
        connection.close();
    }

}
