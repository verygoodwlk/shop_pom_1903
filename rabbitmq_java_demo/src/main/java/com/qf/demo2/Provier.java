package com.qf.demo2;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/12 16:17
 */
public class Provier {

    public static void main(String[] args) throws IOException {
        
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机
        //第四种模型 - direct
        //第五种模型 - topic
        channel.exchangeDeclare("myexchange", "fanout");

        //发送消息给交换机
        String content = "Hello Rabbitmq！";
        channel.basicPublish("myexchange", "", null, content.getBytes());

        connection.close();
    }
}
