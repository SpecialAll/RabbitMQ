package com.zxh.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zxh.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 普通模式
 */
public class Send2 {
    public static final String QUEUE_NAME = "test_queue_confirm1";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用confirmSelect，将channel设置为confirm模式，注意不能是之前已经设置过模式的队列；
        channel.confirmSelect();

        String msg = "hello confirm";
        for(int i=0; i<10; i++){
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }

        if(!channel.waitForConfirms()){
            System.out.println("message send failed");
        } else {
            System.out.println("message send ok");
        }

        channel.close();
        connection.close();
    }
}
