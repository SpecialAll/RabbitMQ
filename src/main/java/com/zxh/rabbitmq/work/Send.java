package com.zxh.rabbitmq.work;

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
