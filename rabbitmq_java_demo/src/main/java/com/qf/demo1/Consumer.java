package com.qf.demo1;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/12 15:29
 */
public class Consumer {

    //创建一个线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(20);

    public static void main(String[] args) throws IOException {
        //1、连接rabbitmq
        Connection connection = ConnectionUtil.getConnection();

        //2、获得channel
        Channel channel = connection.createChannel();

        //3、通过channel创建队列
        channel.queueDeclare("myqueue", false, false, false, null);

        //3、监听指定的队列
        channel.basicConsume("myqueue", true, new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                executorService.submit(() -> {
                    try {
                        System.out.println("消费者接收到消息：" + new String(body, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

    }
}
