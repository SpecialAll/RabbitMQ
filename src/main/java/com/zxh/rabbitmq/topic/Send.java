package com.zxh.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zxh.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String EXCHANGE_NAME = "test_exchange_topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String  msgString = "商品.....";
        channel.basicPublish(EXCHANGE_NAME, "goods.delete", null, msgString.getBytes());

        System.out.println("send msg : "+msgString);

        channel.close();
        connection.close();
    }
}
