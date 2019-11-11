package com.zxh.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {
    /**
     * 获取MQ的连接
     * @return
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置服务地址
        factory.setHost("127.0.0.1");

        //设置端口号
        factory.setPort(5672);

        //设置连接数据库
        factory.setVirtualHost("/ningque_host");

        //设置用户名
        factory.setUsername("ningque");

        //设置密码
        factory.setPassword("zxh929105");

        return factory.newConnection();
    }
}
