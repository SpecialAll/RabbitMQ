package com.zxh.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.zxh.rabbitmq.utils.ConnectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * confirm模式
 */
public class Send3 {
    public static final String QUEUE_NAME = "test_queue_confirm3";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用confirmSelect，将channel设置为confirm模式，注意不能是之前已经设置过模式的队列；
        channel.confirmSelect();

        //未确认消息的标示
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        channel.addConfirmListener(new ConfirmListener() {

            //没有问题的handleAck  成功了调用的方法
            @Override
            public void handleAck(long l, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("handleAack---------multiple");
                    confirmSet.headSet(l+1).clear();
                } else {
                    System.out.println("handleAack---------multiple-----false");
                    confirmSet.remove(l);
                }
            }

            //失败了调用的方法
            @Override
            public void handleNack(long l, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("handleAck---------multiple");
                    confirmSet.headSet(l+1).clear();
                } else {
                    System.out.println("handleAck---------multiple-----false");
                    confirmSet.remove(l);
                }
            }
        });

        String msg = "hello confirm";
        while (true){
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmSet.add(seqNo);
        }
    }
}
