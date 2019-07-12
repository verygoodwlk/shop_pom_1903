package com.qf.demo1;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息提供者
 *
 * @version 1.0
 * @user ken
 * @date 2019/7/12 15:13
 */
public class Provider {

    public static void main(String[] args) throws IOException, TimeoutException {

        //1、连接rabbitmq
        Connection connection = ConnectionUtil.getConnection();

        //2、通过连接获得Channel对象，后续所有交换机、队列、绑定、发送消息等等操作都是通过Channel操作的
        Channel channel = connection.createChannel();

        //3、通过channel创建队列
        channel.queueDeclare("myqueue", false, false, false, null);

        //4、发送消息到指定的队列
        String content = "Hello RabbitMQ!";

        for (int i = 0; i < 10; i++) {
            String c = content + (i + 1);
            channel.basicPublish("", "myqueue", null, c.getBytes("UTF-8"));
        }


        //5、断开连接
        connection.close();
    }
}
