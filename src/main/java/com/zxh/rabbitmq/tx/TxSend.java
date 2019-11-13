package com.zxh.rabbitmq.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zxh.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxSend {
    public static final String QUEUE_NAME = "test_queue_tx";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "hello tx!";

        try {
            channel.txSelect();

            int x = 1/0;

            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("send message tx");
            channel.txCommit();
        } catch (IOException e) {
            channel.txRollback();
            e.printStackTrace();
            System.out.println("send message txRollback");
        }

        channel.close();
        connection.close();
    }
}
